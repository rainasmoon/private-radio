package com.rainasmoon.privateradio.sourcechanel.weibo;

import java.net.URI;

public abstract class WeiboConstants {
	

    public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";

    /**
     * Scope 是 OAuth2.0 授权机制中 authorize 接口的一个参数。通过 Scope，平台将开放更多的微博
     * 核心功能给开发者，同时也加强用户隐私保护，提升了用户体验，用户在新 OAuth2.0 授权页中有权利
     * 选择赋予应用的功能。
     * 
     * 我们通过新浪微博开放平台-->管理中心-->我的应用-->接口管理处，能看到我们目前已有哪些接口的
     * 使用权限，高级权限需要进行申请。
     * 
     * 目前 Scope 支持传入多个 Scope 权限，用逗号分隔。
     * 
     * 有关哪些 OpenAPI 需要权限申请，请查看：http://open.weibo.com/wiki/%E5%BE%AE%E5%8D%9AAPI
     * 关于 Scope 概念及注意事项，请查看：http://open.weibo.com/wiki/Scope
     */
    public static final String SCOPE = 
            "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";
    

    
    /** 通过 code 获取 Token 的 URL */
    public static final String OAUTH2_ACCESS_TOKEN_URL = "https://open.weibo.cn/oauth2/access_token";

	public static final String URL_TOKEN = "https://api.weibo.com/oauth2/authorize?client_id=3226479900&response_type=code&redirect_uri=phttps://api.weibo.com/oauth2/default.html";
	
	public static final String URL_ACCESS_TOKEN = "https://api.weibo.com/oauth2/access_token?client_id=YOUR_CLIENT_ID&client_secret=YOUR_CLIENT_SECRET&grant_type=authorization_code&redirect_uri=YOUR_REGISTERED_REDIRECT_URI&code=CODE";
	
	public static final String URL_GET = "https://api.weibo.com/2/statuses/public_timeline.json";

	public static final String APP_KEY = "3226479900";
	
	public static final String APP_SECRET ="a8e385352d2e29534bfab047cc5277ca";
	
	public static final String REDIRECT_URI_DEFAULT = "https://api.weibo.com/oauth2/default.html";
//	public static final String REDIRECT_URI = "pocketapp20568:authorizationFinished";

	public static final Object RESPONSE_TYPE = "code";

	public static String token = null;
	
	public static String access_token = "2.00xHTFnB5oy2WDa807f7d4a3HqH6PC";
	
}
