package com.rainasmoon.privateradio.business.impl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;

import android.content.Context;
import android.media.AudioManager;
import android.media.AudioManager.OnAudioFocusChangeListener;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.rainasmoon.privateradio.R;
import com.rainasmoon.privateradio.business.MagicRadio;
import com.rainasmoon.privateradio.business.PlayHandlerImpl;
import com.rainasmoon.privateradio.program.AudioProgram;
import com.rainasmoon.privateradio.program.Channel;
import com.rainasmoon.privateradio.program.ChannelImpl;
import com.rainasmoon.privateradio.program.Program;
import com.rainasmoon.privateradio.program.SilenceProgram;
import com.rainasmoon.privateradio.program.TextProgram;
import com.rainasmoon.privateradio.sourcechanel.interneturl.InternetResouceHandler;
import com.rainasmoon.privateradio.sourcechanel.localmedia.LocalMediaConstants;
import com.rainasmoon.privateradio.sourcechanel.localmedia.LocalMediaHandler;
import com.rainasmoon.privateradio.sourcechanel.localmedia.PickerMediaConstants;
import com.rainasmoon.privateradio.sourcechanel.localmedia.SetFileFolder;
import com.rainasmoon.privateradio.sourcechanel.pocket.PocketConstants;
import com.rainasmoon.privateradio.sourcechanel.pocket.PocketHandler;
import com.rainasmoon.privateradio.sourcechanel.rss.RssHandler;
import com.rainasmoon.privateradio.sourcechanel.weibo.WeiboHandler;
import com.rainasmoon.privateradio.utils.Utils;

public class PrivateRadio implements MagicRadio {

	private PlayHandlerImpl handler;
	private List<Channel> channels;
	private int currentChannelId;
	private int subMediaSourceWay;

	@Override
	public void play() {

		playGeneratedPrograms();

	}

	private void playGeneratedPrograms() {

		channels = new ArrayList<Channel>();

		addInitRadioChannel();

		addLocalSoftwareMediaFolder();

		addTestTtsRadioChannel();

		addAllLocalMedia();

		addBonuesChannel();

		addLocalPickFolder();

		addInternetMedia();

		new Thread() {
			@Override
			public void run() {
				Channel c = new ChannelImpl("RSS 电台" + subMediaSourceWay);
				c.addAllPrograms(addRssMedia(com.rainasmoon.privateradio.sourcechanel.rss.RssConstants.rss_list[subMediaSourceWay]));
				channels.add(c);
			}

		}.start();
		new Thread() {
			@Override
			public void run() {
				Channel c = new ChannelImpl("Pocket 电台");
				c.addAllPrograms(playPocket());
				channels.add(c);
			}

		}.start();
		new Thread() {
			@Override
			public void run() {
				Channel c = new ChannelImpl("Weibo 电台");
				c.addAllPrograms(playWeibo());
				channels.add(c);
			}

		}.start();

		playCurrentChannel();

	}

	private void playCurrentChannel() {
		handler = new PlayHandlerImpl(this);

		try {

			Utils.log.info("playing Channel: "
					+ channels.get(currentChannelId).getChannelName());
			handler.playList(channels.get(currentChannelId).getPrograms());

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

	private void addBonuesChannel() {
		Channel c = new ChannelImpl("VIP电台");
		c.addProgram(new TextProgram(
				" 《伊萨卡岛》（希）卡瓦菲斯     当你启程前往伊萨卡，但愿你的道路漫长，充满奇迹，充满发现。莱斯特律戈涅斯巨人，独眼巨人，愤怒的波塞冬海神——不要怕他们：你将不会在途中碰到诸如此类的怪物，只要你高扬你的思想，只要有一种特殊的感觉，接触你的精神和肉体。莱斯特律戈涅斯巨人，独眼巨人，野蛮的波塞冬海神——你将不会跟他们遭遇，除非你将他们一直带进你的灵魂，除非你的灵魂将他们树立在你的面前。但愿你的道路漫长。但愿那里有很多夏天的早晨，当你无比快乐和兴奋地进入你第一次见到的海港：但愿你在腓尼基人的贸易市场停步，购买精美的物件，珍珠母和珊瑚，琥珀和黑檀，各式各样销魂的香水——你要多销魂就有多销魂。愿你走访众多埃及城市，向那些有识之士讨教并继续讨教。 让伊萨卡常在你心中，抵达那里是你此行的目的。但路上不要过于匆促，最好多延长几年，那时当你上得了岛你也就老了，一路所得已经教你富甲四方，用不着伊萨卡来让你财源滚滚。用伊萨卡赋予你如此神奇的旅行，没有它你可不会启程前来。现在它再也没有什么可以给你的了。而如果你发现它原来是这么穷，那可不是伊萨卡想愚弄你。既然那时你已经变得很聪慧，并且见多识广，你也就不会不明白，这伊萨卡意味着什么。"));

		c.addProgram(new SilenceProgram());
		channels.add(c);

	}

	private void addInitRadioChannel() {
		Channel c = new ChannelImpl("初始化电台");

		c.addProgram(new TextProgram(
				"问我爱你有多深，私人定制电台代表我的心。欢迎来到这高端大气上当了的个人电台播报软件。"));
		
//		c.addProgram(new SilenceProgram());

		c.addProgram(new TextProgram(
				"You have to let it all go, Neo, fear, doubt, and disbelief. Free your mind. "));
//		c.addProgram(new SilenceProgram());
		
		c.addProgram(new TextProgram(
				"This will be passed to ARS if there is a ARS error, they are all related with the validation of business logic such as: we can’t pass “Income Protection” & “Income Protection (super)” to ARS simultaneously."));

		channels.add(c);
	}

	private void addTestTtsRadioChannel() {
		Channel c = new ChannelImpl("测试电台");

		c.addProgram(new TextProgram(
				"我在试图把你这种生命体作一个归类的时候，启示来了，所以我知道，你其实不是哺乳动物，我们这个星球上的每一种哺乳动物都有一种适应环境变化，取得平衡的本能演化，但你们人却没有，你们找到一个地方，然后就繁殖、繁殖、繁殖，直到所有的自然资源都被用尽，你们要想生存，唯一的办法就是扩散出去，侵占另一个地方，这个星球上另有一种生命体生存方式同你们相象，想知道是什么吗？病毒！人类是一种疾病，本星球的癌症，一场瘟疫。"));

//		c.addProgram(new SilenceProgram());
		
		c.addProgram(new TextProgram(
				"You have to let it all go, Neo, fear, doubt, and disbelief. Free your mind. "));
//		c.addProgram(new SilenceProgram());

		channels.add(c);
	}

	private void addLocalSoftwareMediaFolder() {
		for (String s : LocalMediaConstants.LOCAL_FOLDERS) {
			Utils.log.info("the folder is:" + s);
			File folder = new File(s);
			addFile(folder);
		}

	}

	private void addLocalPickFolder() {
		for (String s : PickerMediaConstants.LOCAL_FOLDERS) {
			Utils.log.info("the folder is:" + s);
			File folder = new File(s);
			addFile(folder);
		}

	}

	private void addFile(File folder) {
		Channel c = new ChannelImpl(folder.getName() + "电台");
		for (File f : folder.listFiles()) {
			if (f.isFile()) {
				if (f.getName().endsWith(".mp3")) {
					c.addProgram(new AudioProgram(f));
				}
			} else {
				addFile(f);
			}
		}
		channels.add(c);
	}

	private void addInternetMedia() {

		try {
			InternetResouceHandler handler = new InternetResouceHandler();
			List<String> l = handler.getInternetMedia();
			Channel c = new ChannelImpl("音乐电台");

			for (String url : l) {
				c.addProgram(new AudioProgram(url));
			}

			channels.add(c);
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

	private List<Program> addRssMedia(String rssUrl) {

		List<Program> l = new ArrayList<Program>();

		final RssHandler rssHandler = new RssHandler();

		List<String> msgList = rssHandler.getText(rssUrl);
		for (int i = 0; i < msgList.size(); i++) {
			String say = msgList.get(i);
			Utils.log.info("msg:" + say);
			l.add(new TextProgram(say));
		}

		return l;
	}

	private void addAllLocalMedia() {
		LocalMediaHandler localMediaHandler = new LocalMediaHandler();
		List<Long> list = localMediaHandler.getBanchOfLocalMedia();

		Channel c = new ChannelImpl("本地所有音乐电台");
		for (Long id : list) {
			c.addProgram(new AudioProgram(id));
		}

		channels.add(c);

	}

	private List<Program> playPocket() {

		List<Program> l = new ArrayList<Program>();

		PocketHandler pocketHandler = new PocketHandler();
		List<String> msgList = pocketHandler.retrivePocketList();
		for (int i = 0; i < msgList.size(); i++) {
			String say = msgList.get(i);
			Utils.log.info("msg:" + say);
			l.add(new TextProgram(say));
		}

		return l;
	}

	private List<Program> playWeibo() {
		List<Program> l = new ArrayList<Program>();

		WeiboHandler weiboHandler = new WeiboHandler();
		List<String> msgList = weiboHandler.retriveWeiboList();

		for (int i = 0; i < msgList.size(); i++) {
			String say = msgList.get(i);
			Utils.log.info("msg:" + say);
			l.add(new TextProgram(say));
		}
		return l;
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
		currentChannelId++;
		if (currentChannelId >= channels.size()) {
			currentChannelId = 0;
		}
		playCurrentChannel();
	}

	@Override
	public void preChannel() {
		// TODO Auto-generated method stub

	}

	@Override
	public void ListAllChannels() {
		// TODO Auto-generated method stub

	}

	public void unlike() {
		channels.get(currentChannelId).unlike();

		if (channels.get(currentChannelId).getUnlikeCounter() > 3) {
			nextChannel();
			return;
		}
		handler.next();

	}

}
