package pss.www.platform.content.generators.internal;

/**
 * Enumeration of the serializers that were originally defined in the Cocoon
 * sitemap. Each serializer declares its mime type, the character encoding used
 * and whether the output should be compressed using GZIP.
 */
public enum CocoonSerializer {

  XML("xml", "text/xml", "UTF-8", false),
  HTML("html", "text/html", "UTF-8", true),
  HTML_DECODED("html-decoded", "text/html", "ISO-8859-5", true),
  HTML_NOCOMPRESS("html-nocompress", "text/html", "ISO-8859-5", false),
  GRAPH("graph", "text/html", "UTF-8", true),
  MAP("map", "text/html", "UTF-8", true),
  EXCEL("excel", "application/vnd.ms-excel", "UTF-8", false),
  NONE("none", "application/vnd.ms-excel", "UTF-8", false),
  JSON("json", "application/json", "UTF-8", false),
  JSON_COMPRESS("json-compress", "application/json", "UTF-8", true),
  MOBILE("mobile", "application/json", "UTF-8", true),
  CSV("csv", "text/csv", "UTF-8", false),
  JS("js", "text/javascript", "UTF-8", false),
  CSS("css", "text/css", "UTF-8", false);

	private final String name;
	private final String mimeType;
	private final String encoding;
	private final boolean gzip;

	CocoonSerializer(String name, String mimeType, String encoding, boolean gzip) {
		this.name = name;
		this.mimeType = mimeType;
		this.encoding = encoding;
		this.gzip = gzip;
	}

	public String getMimeType() {
		return mimeType;
	}

	public String getEncoding() {
		return encoding;
	}

	public boolean isGzip() {
		return gzip;
	}

	public String getName() {
		return name;
	}

	/**
	 * Resolves the serializer by its sitemap name.
	 */
	public static CocoonSerializer from(String name) {
		for (CocoonSerializer s : values()) {
			if (s.name.equalsIgnoreCase(name)) {
				return s;
			}
		}
		throw new IllegalArgumentException("Unknown serializer: " + name);
	}
}
