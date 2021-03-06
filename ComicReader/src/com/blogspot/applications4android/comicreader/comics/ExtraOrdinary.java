package com.blogspot.applications4android.comicreader.comics;

import java.io.BufferedReader;
import java.io.IOException;

import com.blogspot.applications4android.comicreader.comictypes.IndexedComic;
import com.blogspot.applications4android.comicreader.core.Strip;
import com.blogspot.applications4android.comicreader.exceptions.ComicLatestException;


public class ExtraOrdinary extends IndexedComic {

	@Override
	protected String getFrontPageUrl() {
		return "http://www.exocomics.com";
	}

	@Override
	public String getComicWebPageUrl() {
		return "http://www.exocomics.com";
	}

	@Override
	protected int parseForLatestId(BufferedReader reader) throws IOException, ComicLatestException {
		String str;
		String final_str = null;
		while ((str = reader.readLine()) != null) {
			int index1 = str.indexOf("REFRESH");
			if (index1 != -1) {
				final_str = str;
			}
		}
		if(final_str == null) {
			String msg = "Failed to get the latest id for "+this.getClass().getSimpleName();
			ComicLatestException e = new ComicLatestException(msg);
			throw e;
		}
		final_str = final_str.replaceAll(".*url=http://www.exocomics.com/","");
		final_str = final_str.replaceAll("\".*","");
	   	return Integer.parseInt(final_str);
	}

	@Override
	public String getStripUrlFromId(int num) {
		return "http://www.exocomics.com/" + num;
	}

	@Override
	protected int getIdFromStripUrl(String url) {
		return Integer.parseInt(url.replaceAll("http.*exocomics.com/", ""));
	}

	@Override
	protected boolean htmlNeeded() {
		return true;
	}

	@Override
	protected String parse(String url, BufferedReader reader, Strip strip)
			throws IOException {
		String str;
		int id = getIdFromStripUrl(url);
		String final_str = "http://www.exocomics.com/comics/comics/" + id + ".jpg";
		String final_title = "Extra Ordinary: " + id;
		String final_text = null;
		while ((str = reader.readLine()) != null) {
			int index1 = str.indexOf("class=\"comic-item");
			if (index1 != -1) {
				final_text = str;
			}
		}
		final_text = final_text.replaceAll(".*title=\"","");
		final_text = final_text.replaceAll("\".*","");
		strip.setTitle(final_title); 
		strip.setText(final_text);
    	return final_str;
	}
}
