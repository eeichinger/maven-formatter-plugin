package com.relativitas.maven.plugins.formatter;

/*
 * Copyright 2010. All work is copyrighted to their respective author(s),
 * unless otherwise stated.
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

import java.io.IOException;
import java.io.Reader;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.digester.Digester;
import org.xml.sax.SAXException;

/**
 * This config reader read Eclipse's config file for eclipse formatter.
 * 
 * @author jecki
 */
public class ConfigReader {

	/**
	 * Read from the <code>reader</code> and return it's configuration settings
	 * as a {@link Map}.
	 * 
	 * @param reader
	 * @return return unmodifiable {@link Map} with all the configurations read
	 *         from the config file, or empty {@link Map} if there's an
	 *         exception occured while reading the reader, e.g.: invalid XML.
	 */
	public Map read(Reader reader) {
		Digester digester = new Digester();
		digester.addRuleSet(new RuleSet());

		Map config = Collections.emptyMap();
		try {
			Object result = digester.parse(reader);
			if (result == null && !(result instanceof List)) {
				return config;
			}

			List list = (List) result;
			if (list.size() == 0) {
				return config;
			}

			config = Collections.unmodifiableMap((Map) list.get(0));
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		}

		return config;
	}
}
