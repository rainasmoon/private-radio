package com.rainasmoon.privateradio.sourcechanel.rss;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.util.Log;

public class RssHandler extends RssConstants{

	java.util.logging.Logger log = java.util.logging.Logger.getLogger("wh:");

	public List<String> getText(String rssUrl) {
		
		List<String> l = new ArrayList<String>();
				
		RSSFeed rsss = getFeed(rssUrl);

		if (rsss != null) {

			for (int i = 0; i< rsss.getItemCount(); i++) {
				l.add(rsss.getItem(i).getTitle() + " " + rsss.getItem(i).getDescription());
			}
		}
		return l;
	}

	private RSSFeed getFeed(String urlString) {
		try {
			log.info("begin get feed.");
			URL url = new URL(urlString);
			SAXParserFactory factory = SAXParserFactory.newInstance(); // 构建Sax解析工厂
			SAXParser parser = factory.newSAXParser(); // 使用Sax解析工厂构建Sax解析器
			XMLReader xmlreader = parser.getXMLReader(); // 使用Sax解析器构建xml Reader

			RSSSourceHandler rssHandler = new RSSSourceHandler(); // 构建自定义的RSSHandler作为xml
														// Reader的处理器（或代理）
			xmlreader.setContentHandler(rssHandler); // 构建自定义的RSSHandler作为xml
														// Reader的处理器（或代理）

			log.info("reading feed.");
			InputSource is = new InputSource(url.openStream()); // 使用url打开流,并将流作为xml
																// Reader的输入源并解析

			log.info("read feed finished.");
			xmlreader.parse(is);

			log.info("get feed ended.");
			RSSFeed feed = rssHandler.getFeed();
			log.info("realy end.");

			return feed; // 将解析结果作为 RSSFeed 对象返回
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
}
