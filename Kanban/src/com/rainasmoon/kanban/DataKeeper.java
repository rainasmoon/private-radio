/*
 * Copyright (C) 2010-2013 The SINA WEIBO Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rainasmoon.kanban;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 该类定义了微博授权时所需要的参数。
 * 
 * @author SINA
 * @since 2013-10-07
 */
public class DataKeeper {
	private static final String PREFERENCES_KANBAN_NAME = "com_rainasmoon_kanban";

	private static final String KEY_BACKLOG = "backlog";
	private static final String KEY_PLAN = "plan";
	private static final String KEY_INPROCESS = "inProcess";
	private static final String KEY_COMPLETED = "completed";

	private DataKeeper() {
	}

	/**
	 * 保存 Token 对象到 SharedPreferences。
	 * 
	 * @param context
	 *            应用程序上下文环境
	 * @param token
	 *            Token 对象
	 */
	public static void writeData(Context context, DataSet dataSet) {
		if (null == context || null == dataSet) {
			return;
		}

		SharedPreferences pref = context.getSharedPreferences(
				PREFERENCES_KANBAN_NAME, Context.MODE_APPEND);
		Editor editor = pref.edit();
		editor.putStringSet(KEY_BACKLOG, dataSet.getBacklog());
		editor.putStringSet(KEY_PLAN, dataSet.getPlan());
		editor.putStringSet(KEY_INPROCESS, dataSet.getInProcess());
		editor.putStringSet(KEY_COMPLETED, dataSet.getCompleted());

		editor.commit();
	}

	/**
	 * 从 SharedPreferences 读取 Token 信息。
	 * 
	 * @param context
	 *            应用程序上下文环境
	 * 
	 * @return 返回 Token 对象
	 */
	public static DataSet readData(Context context) {
		if (null == context) {
			return null;
		}

		DataSet dataSet = new DataSet();
		SharedPreferences pref = context.getSharedPreferences(
				PREFERENCES_KANBAN_NAME, Context.MODE_APPEND);
		dataSet.setBacklog(pref.getStringSet(KEY_BACKLOG, null));
		dataSet.setPlan(pref.getStringSet(KEY_PLAN, null));
		dataSet.setInProcess(pref.getStringSet(KEY_INPROCESS, null));
		dataSet.setCompleted(pref.getStringSet(KEY_COMPLETED, null));
		return dataSet;
	}

	/**
	 * 清空 SharedPreferences 中 Token信息。
	 * 
	 * @param context
	 *            应用程序上下文环境
	 */
	public static void clear(Context context) {
		if (null == context) {
			return;
		}

		SharedPreferences pref = context.getSharedPreferences(
				PREFERENCES_KANBAN_NAME, Context.MODE_APPEND);
		Editor editor = pref.edit();
		editor.clear();
		editor.commit();

	}
}
