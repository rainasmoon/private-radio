package com.rainasmoon.privateradio.program;

import com.rainasmoon.privateradio.business.Play;
import com.rainasmoon.privateradio.utils.Utils;

public abstract class Program implements Play {

	public void stop() {
		
		Utils.log.info("Program stop is runn...");
	}

}
