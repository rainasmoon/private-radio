package com.rainasmoon.privateradio.business;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.util.Log;

import com.rainasmoon.privateradio.program.AudioProgram;
import com.rainasmoon.privateradio.program.Program;
import com.rainasmoon.privateradio.program.TextProgram;
import com.rainasmoon.privateradio.sourcechanel.interneturl.InternetResouceHandler;
import com.rainasmoon.privateradio.sourcechanel.localmedia.LocalMediaHandler;
import com.rainasmoon.privateradio.sourcechanel.localmedia.SetFileFolder;
import com.rainasmoon.privateradio.sourcechanel.pocket.Constants;
import com.rainasmoon.privateradio.sourcechanel.pocket.PocketHandler;
import com.rainasmoon.privateradio.sourcechanel.rss.RssHandler;
import com.rainasmoon.privateradio.sourcechanel.weibo.WeiboHandler;
import com.rainasmoon.privateradio.utils.Utils;

public class PrivateRadio implements MagicRadio {
	
	enum Channel{
		LOCAL_MEDIA(0), SELECT_LOCAL_MEDIA(1), INTERNET_MEDIA(2), RSS(3), POCKET(4), WEIBO(5);
		
		private int channelId;
		
		private Channel(int id) {
			channelId = id;
		}
		 
		
		
		private Channel getChannel(int id) {
			
			switch(id) {
			case 0:
				return LOCAL_MEDIA;
			case 1:
				return SELECT_LOCAL_MEDIA;
			case 2:
				return INTERNET_MEDIA;
			case 3:
				return RSS;
			case 4:
				return POCKET;
			case 5:
				return WEIBO;
				
			}
			return null;
		} 
		public Channel nextChannel() {
			if (channelId == 5) {
				return LOCAL_MEDIA;
			}
			return getChannel(channelId+1);
		}
	}
	
	private Channel currentChannel = Channel.LOCAL_MEDIA;
	

	@Override
	public void play() {
		
		switch (currentChannel) {
		case LOCAL_MEDIA:
			playLocalMedia();
			break;
		case SELECT_LOCAL_MEDIA:
			playSelectLocalMedia();
			break;
		case INTERNET_MEDIA:
			playInternetMedia();
			break;
		case RSS:
			playRssMedia();
			break;
		case POCKET:
			playPocket();
			break;
		case WEIBO:
			playWeibo();
			break;
			default:
				break;
				
		}
	
		// sayHelloToEveryOne();

		// testTtsMedia();//works!

	}

	private void playInternetMedia() {
		
		
		try {
			InternetResouceHandler handler = new InternetResouceHandler();
			handler.getInternetMedia();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void playSelectLocalMedia() {
		if (com.rainasmoon.privateradio.sourcechanel.localmedia.Constants.MY_PICK_SONG_URI == null) {
			new SetFileFolder().pick();
		}
		else {

			Program program = new AudioProgram(com.rainasmoon.privateradio.sourcechanel.localmedia.Constants.MY_PICK_SONG_URI);
			program.play();
		}

	}

	private void testTtsMedia() {
		Program program = new TextProgram(
				" 《伊萨卡岛》（希）卡瓦菲斯     当你启程前往伊萨卡，但愿你的道路漫长，充满奇迹，充满发现。莱斯特律戈涅斯巨人，独眼巨人，愤怒的波塞冬海神——不要怕他们：你将不会在途中碰到诸如此类的怪物，只要你高扬你的思想，只要有一种特殊的感觉，接触你的精神和肉体。莱斯特律戈涅斯巨人，独眼巨人，野蛮的波塞冬海神——你将不会跟他们遭遇，除非你将他们一直带进你的灵魂，除非你的灵魂将他们树立在你的面前。但愿你的道路漫长。但愿那里有很多夏天的早晨，当你无比快乐和兴奋地进入你第一次见到的海港：但愿你在腓尼基人的贸易市场停步，购买精美的物件，珍珠母和珊瑚，琥珀和黑檀，各式各样销魂的香水——你要多销魂就有多销魂。愿你走访众多埃及城市，向那些有识之士讨教并继续讨教。 让伊萨卡常在你心中，抵达那里是你此行的目的。但路上不要过于匆促，最好多延长几年，那时当你上得了岛你也就老了，一路所得已经教你富甲四方，用不着伊萨卡来让你财源滚滚。用伊萨卡赋予你如此神奇的旅行，没有它你可不会启程前来。现在它再也没有什么可以给你的了。而如果你发现它原来是这么穷，那可不是伊萨卡想愚弄你。既然那时你已经变得很聪慧，并且见多识广，你也就不会不明白，这伊萨卡意味着什么。");
		program.play();
	}

	private void sayHelloToEveryOne() {
		Program program = new TextProgram(
				"问我爱你有多深，私人定制电台代表我的心。欢迎来到这高端大气上当了的个人电台播报软件。");
		program.play();
	}

	private void playRssMedia() {
		RssHandler rssHandler = new RssHandler();
		List<String> msgList = rssHandler.getText();
		for (int i = 0; i < msgList.size(); i++) {
			String say = msgList.get(i);
			Utils.log.info("msg:" + say);
			Program program = new TextProgram(say);
			program.play();
		}

	}

	private void playLocalMedia() {
		LocalMediaHandler localMediaHandler = new LocalMediaHandler();
		long id = localMediaHandler.getBanchOfLocalMedia();

		Program program = new AudioProgram(id);

		program.play();
	}

	private void playPocket() {
		PocketHandler pocketHandler = new PocketHandler();
		String msg = pocketHandler.retrivePocketList();

		Utils.log.info("we get msg:" + msg);
		Program program = new TextProgram(msg);
		program.play();
	}

	private void playWeibo() {
		WeiboHandler weiboHandler = new WeiboHandler();
		String msg = weiboHandler.retriveWeiboList();

		Utils.log.info("we get msg:" + msg);
		Program program = new TextProgram(msg);
		program.play();
	}
	
	@Override
	public void pause() {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

	@Override
	public void start() {
		// TODO Auto-generated method stub

	}

	@Override
	public void nextChannel() {
		currentChannel = currentChannel.nextChannel();

	}

	@Override
	public void preChannel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void generateProgramSchedule() {
		// TODO Auto-generated method stub

	}

}
