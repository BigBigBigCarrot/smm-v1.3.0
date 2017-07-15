package com.david.util;

import java.io.UnsupportedEncodingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;

public class StringUtil extends org.apache.commons.lang3.StringUtils
{
	private static final char SEPARATOR = '_';
	private static final String CHARSET_NAME = "UTF-8";

	public StringUtil()
	{
	}

	public static byte[] getBytes(String str)
	{
		if (str != null)
		{
			try
			{
				return str.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e)
			{
				return null;
			}
		}
		return null;
	}

	public static String toString(byte[] bytes)
	{
		try
		{
			return new String(bytes, "UTF-8");
		} catch (UnsupportedEncodingException e)
		{
		}
		return "";
	}

	public static String parseString(int value)
	{
		return String.valueOf(value);
	}

	public static String parseString(long value)
	{
		return String.valueOf(value);
	}

	public static String parseString(Object value)
	{
		if (value == null)
		{
			return null;
		}
		return value.toString();
	}

	public static String replaceHtml(String html)
	{
		if (isBlank(html))
		{
			return "";
		}
		String regEx = "<.+?>";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(html);
		String s = m.replaceAll("");
		return s;
	}

	public static String replaceMobileHtml(String html)
	{
		if (html == null)
		{
			return "";
		}
		return html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
	}

	public static String abbr(String str, int length)
	{
		if (str == null)
		{
			return "";
		}
		try
		{
			StringBuilder sb = new StringBuilder();
			int currentLength = 0;
			for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray())
			{
				currentLength += String.valueOf(c).getBytes("GBK").length;
				if (currentLength <= length - 3)
				{
					sb.append(c);
				} else
				{
					sb.append("...");
					break;
				}
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}
		return "";
	}

	public static Double toDouble(Object val)
	{
		if (val == null)
		{
			return Double.valueOf(0.0D);
		}
		try
		{
			return Double.valueOf(trim(val.toString()));
		} catch (Exception e)
		{
		}
		return Double.valueOf(0.0D);
	}

	public static Float toFloat(Object val)
	{
		return Float.valueOf(toDouble(val).floatValue());
	}

	public static Long toLong(Object val)
	{
		return Long.valueOf(toDouble(val).longValue());
	}

	public static Integer toInteger(Object val)
	{
		return Integer.valueOf(toLong(val).intValue());
	}

	public static String toCamelCase(String s)
	{
		if (s == null)
		{
			return null;
		}

		s = s.toLowerCase();

		StringBuilder sb = new StringBuilder(s.length());
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);

			if (c == '_')
			{
				upperCase = true;
			} else if (upperCase)
			{
				sb.append(Character.toUpperCase(c));
				upperCase = false;
			} else
			{
				sb.append(c);
			}
		}

		return sb.toString();
	}

	public static String toCapitalizeCamelCase(String s)
	{
		if (s == null)
		{
			return null;
		}
		s = toCamelCase(s);
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}

	public static String toUnderScoreCase(String s)
	{
		if (s == null)
		{
			return null;
		}

		StringBuilder sb = new StringBuilder();
		boolean upperCase = false;
		for (int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);

			boolean nextUpperCase = true;

			if (i < s.length() - 1)
			{
				nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
			}

			if ((i > 0) && (Character.isUpperCase(c)))
			{
				if ((!upperCase) || (!nextUpperCase))
				{
					sb.append('_');
				}
				upperCase = true;
			} else
			{
				upperCase = false;
			}

			sb.append(Character.toLowerCase(c));
		}

		return sb.toString();
	}

}
