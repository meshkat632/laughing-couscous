package com.sixt.platform.interview;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PageInspector {

  private static Pattern patternDomainName;
  private static Matcher matcher;
  private static final String DOMAIN_NAME_PATTERN 
	= "([a-zA-Z0-9]([a-zA-Z0-9\\-]{0,61}[a-zA-Z0-9])?\\.)+[a-zA-Z]{2,6}";

  private static final List<String> jsLibNames = Arrays.asList("jquery", "bootstrap", "reactjs", "angular", "vue");

  static {
	patternDomainName = Pattern.compile(DOMAIN_NAME_PATTERN);
  }
	
  public static void main(String[] args) {

	String request = "https://www.nintendo.de";
	  try {

		  Document doc = Jsoup
				  .connect(request)
				  .userAgent(
						  "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
				  .timeout(5000).get();

		  Elements scripts = doc.getElementsByTag("script");
		  for (Element script : scripts) {
              if(script.hasAttr("src")){
                  System.out.println(" - "+script.attr("src"));
              }
		  }

	  } catch (IOException e) {
		  e.printStackTrace();
	  }
  }

    private static String getJavascriptLibraryName(String url){
        for (String jsLibName: jsLibNames) {
            if(url.toLowerCase().contains(jsLibName))
                return jsLibName;
        }
        return "UNKNOWN";

    }
  private static String getDomainName(String url){
		
	String domainName = "";
	matcher = patternDomainName.matcher(url);
	if (matcher.find()) {
		domainName = matcher.group(0).toLowerCase().trim();
	}
	return domainName;
		
  }
	
  public static Set<String> getJavascripts(String request) {
		
	Set<String> result = new HashSet<String>();
	System.out.println("Sending request..." + request);

      try {

          // need http protocol, set this as a Google bot agent :)
          Document doc = Jsoup
                  .connect(request)
                  .userAgent(
                          "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")
                  .timeout(5000).get();


          // get all links
          //Elements links = doc.select("a[href]");
          Elements scripts = doc.getElementsByTag("script");
          for (Element script : scripts) {
              if(script.hasAttr("src")){
                  System.out.println(" - "+script.attr("src"));
                  result.add(getJavascriptLibraryName(script.attr("src")));
              }

          }

      } catch (IOException e) {
          e.printStackTrace();
      }


		
	return result;
  }

}