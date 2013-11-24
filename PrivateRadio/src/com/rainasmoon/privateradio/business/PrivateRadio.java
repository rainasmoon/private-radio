package com.rainasmoon.privateradio.business;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.content.Context;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;

import com.rainasmoon.privateradio.R;
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

	enum Channel {
		SELECT_LOCAL_MEDIA(1, "Picker Media"), INTERNET_MEDIA(
				2, "Internet Song"), RSS(3, "RSS"), POCKET(4, "Pocket"), WEIBO(
				5, "Weibo"), LOCAL_MEDIA(6, "Local Media"), LOCAL_FOLDER(0, "Local Folder");

		private int channelId;
		private String channelName;

		private Channel(int id, String name) {
			channelId = id;
			channelName = name;
		}
		
		@Override
		public String toString() {
			return channelName;
		}

		private Channel getChannel(int id) {

			switch (id) {
			case 0:
				return LOCAL_FOLDER;
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
			case 6:
				return LOCAL_MEDIA;

			}
			return null;
		}

		public Channel nextChannel() {
			if (channelId == Channel.values().length -1) {
				return LOCAL_MEDIA;
			}
			return getChannel(channelId + 1);
		}
	}

	private Channel currentChannel = Channel.LOCAL_FOLDER;
	private Program program;

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
		case LOCAL_FOLDER:
			playLocalFolfer();
			break;
		default:
			break;

		}

		// sayHelloToEveryOne();

		// testTtsMedia();//works!

		Button startButton = (Button) Utils.activity
				.findViewById(R.id.start_radio);
		startButton.setText(currentChannel.toString() + " is playing...");

	}

	private void playLocalFolfer() {
		
		File folder = new File("/storage/sdcard0/ttpod/song");
		Utils.log.info("the folder is:" + folder);
		for (File f : folder.listFiles()) {
			program = new AudioProgram(f);
			program.play();
		}
			
		

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
		} else {

			program = new AudioProgram(
					com.rainasmoon.privateradio.sourcechanel.localmedia.Constants.MY_PICK_SONG_URI);
			program.play();
		}

	}

	private void testTtsMedia() {
		program = new TextProgram(
				" 《伊萨卡岛》（希）卡瓦菲斯     当你启程前往伊萨卡，但愿你的道路漫长，充满奇迹，充满发现。莱斯特律戈涅斯巨人，独眼巨人，愤怒的波塞冬海神——不要怕他们：你将不会在途中碰到诸如此类的怪物，只要你高扬你的思想，只要有一种特殊的感觉，接触你的精神和肉体。莱斯特律戈涅斯巨人，独眼巨人，野蛮的波塞冬海神——你将不会跟他们遭遇，除非你将他们一直带进你的灵魂，除非你的灵魂将他们树立在你的面前。但愿你的道路漫长。但愿那里有很多夏天的早晨，当你无比快乐和兴奋地进入你第一次见到的海港：但愿你在腓尼基人的贸易市场停步，购买精美的物件，珍珠母和珊瑚，琥珀和黑檀，各式各样销魂的香水——你要多销魂就有多销魂。愿你走访众多埃及城市，向那些有识之士讨教并继续讨教。 让伊萨卡常在你心中，抵达那里是你此行的目的。但路上不要过于匆促，最好多延长几年，那时当你上得了岛你也就老了，一路所得已经教你富甲四方，用不着伊萨卡来让你财源滚滚。用伊萨卡赋予你如此神奇的旅行，没有它你可不会启程前来。现在它再也没有什么可以给你的了。而如果你发现它原来是这么穷，那可不是伊萨卡想愚弄你。既然那时你已经变得很聪慧，并且见多识广，你也就不会不明白，这伊萨卡意味着什么。");
		program.play();
	}

	private void sayHelloToEveryOne() {
		program = new TextProgram(
				"问我爱你有多深，私人定制电台代表我的心。欢迎来到这高端大气上当了的个人电台播报软件。");
		program.play();
	}

	private void playRssMedia() {
				
		final RssHandler rssHandler = new RssHandler();
				
		List<String> msgList = rssHandler.getText();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < msgList.size(); i++) {
			String say = msgList.get(i);
			Utils.log.info("msg:" + say);
			sb.append(say);
		}
		
		program = new TextProgram(sb.toString());
		program.play();

	}

	private void playLocalMedia() {
		LocalMediaHandler localMediaHandler = new LocalMediaHandler();
		long id = localMediaHandler.getBanchOfLocalMedia();

		program = new AudioProgram(id);

		program.play();
	}

	private void playPocket() {
		PocketHandler pocketHandler = new PocketHandler();
		List<String> msgList = pocketHandler.retrivePocketList();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < msgList.size(); i++) {
			String say = msgList.get(i);
			Utils.log.info("msg:" + say);
			sb.append(say);
		}
		
		program = new TextProgram(sb.toString());
		program.play();
	}

	private void playWeibo() {
		WeiboHandler weiboHandler = new WeiboHandler();		
		List<String> msgList = weiboHandler.retriveWeiboList();
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < msgList.size(); i++) {
			String say = msgList.get(i);
			Utils.log.info("msg:" + say);
			sb.append(say);
		}
		
		program = new TextProgram(sb.toString());
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
		program.stop();
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
