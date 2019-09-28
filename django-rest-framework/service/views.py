import re
import random
import requests

import requests_cache

from urllib.request import urlopen

from bs4 import BeautifulSoup

from django.shortcuts import render
from django.shortcuts import get_object_or_404
from django.utils.decorators import method_decorator
from django.views.decorators.cache import cache_page

from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status

from .digital_media_directory import URLS


CLEAN_HTML_RE = re.compile('<.*?>|&([a-z0-9]+|#[0-9]{1,6}|#x[0-9a-f]{1,6});')
GET_URL_FROM_SRC_RE = re.compile('src="([^"]+)"')
DESCRIPTION_TAG = ['media:description', 'description', 'summary']
TAGS = {
    'title': ['media:text', 'media:title', 'title'],
    'date': ['pubDate', 'dc:date', 'dc:created', 'dc:modified', 'updated'],
    'link': ['link'],
    'description': DESCRIPTION_TAG,
    'image_url': ['link', 'media:thumbnail', 'enclosure', 'imagen', 'image', 'thumbimage'] + DESCRIPTION_TAG,
}



class Info(APIView):

    @method_decorator(cache_page(3600))
    def get(self, request):

        # Example of GET request.
        json_data = self.get_data_from_xml('france24-fr')

        return Response(json_data)

    @method_decorator(cache_page(3600))
    def post(self, request):
        digital_media = request.data['digitalMediaName']
        json_data = self.get_data_from_xml(digital_media)

        return Response(json_data)

    def get_data_from_xml(self, digital_media_name):
        data = []

        # Caching request.
        expire_after = random.randint(240, 360)
        requests_cache.install_cache(expire_after=expire_after)  # 4-6 minutes.

        for url in URLS[digital_media_name]:

            xml = requests.get(url)
            soup = BeautifulSoup(xml.content, 'xml')

            # Servers that need agents.
            if soup.select_one('TITLE') and 'denied' in soup.select_one('TITLE').text.lower():
                agent = {
                    'User-Agent': 'Mozilla/5.0 (Windows NT 6.3; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/59.0.3071.115 Safari/537.36'}
                xml = requests.get(url, headers=agent)
                soup = BeautifulSoup(xml.content, 'html.parser')

            # Tags with diferent name to 'item'.
            items = soup.select('item')
            if not items:
                items = soup.select('entry')

            for item in items:
                data.append({
                    "title": self.get_value_node(item, TAGS['title']),
                    "date": self.get_value_node(item, TAGS['date']),
                    "link": self.get_value_node(item, TAGS['link']),
                    "description": self.get_value_node(item, TAGS['description']),
                    "image_url": self.get_value_node(item, TAGS['image_url'], value_image=True),
                })

        return data

    def get_value_node(self, item, node_names, value_image=None):
        for node_name in node_names:
            node = item.find(node_name)

            # Image in attribute link (there are two link item).
            if node_name == 'link' and value_image:
                node = item.find('link', {'type': 'image/jpeg'})
                if node:
                    return node['href']

            if not node:
                continue

            if value_image:
                # Search src= in description tag.
                img_url = re.search(GET_URL_FROM_SRC_RE, node.text)
                if img_url:
                    return img_url[0].replace('"', '').replace('src=', '')
                elif node.has_attr('url'):
                    return node['url']
                elif 'http' not in node.text:
                    continue

            # If link is empty but has url in the href attribute.
            if node_name == 'link' and not node.text and node.has_attr('href') and not node.has_attr('type'):
                return node['href']

            return self.clean_string(node.text)

        return None

    def clean_string(self, text):
        return re.sub(CLEAN_HTML_RE,
                      '', text).replace('\"', '').replace('\n', '').replace('   ', ' ').replace('  ', ' ').replace('\\u0027', "'").strip()
