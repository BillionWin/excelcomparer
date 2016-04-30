package net.ojava.openkit.excelcomparer.util;

import java.text.NumberFormat;
import java.util.Locale;

public class Similarity {

	public static void main(String[] args) {

		String strA = "09003648";

		String strB = "09003648 台胞证";
		
		//System.out.println(getLevenshteinDistance(strA, strB));

		double result = SimilarDegree(strA, strB);
		
		if (result >= 0.7) {
			System.out.println("相似度很高！" + similarityResult(result) + result);

		} else {

			System.out.println("相似度不高" + similarityResult(result) + result);

		}

		System.out.println();

	}

	/**
	 * 
	 * 相似度转百分比
	 */

	public static String similarityResult(double resule) {

		return NumberFormat.getPercentInstance(new Locale("en ", "US "))
				.format(resule);

	}

	/**
	 * 
	 * 相似度比较
	 * 
	 * @param strA
	 * 
	 * @param strB
	 * 
	 * @return
	 */

	public static double SimilarDegree(String strA, String strB) {

		String newStrA = removeSign(strA);

		String newStrB = removeSign(strB);

		int temp = Math.max(newStrA.length(), newStrB.length());

		//int temp2 = longestCommonSubstring(newStrA, newStrB).length();
		int temp2 = getLevenshteinDistance(newStrA, newStrB);

		return (temp - temp2) * 1.0 / temp;

	}

	private static String removeSign(String str) {

		StringBuffer sb = new StringBuffer();

		for (char item : str.toCharArray())

			if (charReg(item)) {

				// System.out.println("--"+item);

				sb.append(item);

			}

		return sb.toString();

	}

	private static boolean charReg(char charValue) {

		return (charValue >= 0x4E00 && charValue <= 0X9FA5)

		|| (charValue >= 'a' && charValue <= 'z')

		|| (charValue >= 'A' && charValue <= 'Z')

		|| (charValue >= '0' && charValue <= '9');

	}

	@SuppressWarnings("unused")
	private static String longestCommonSubstring(String strA, String strB) {

		char[] chars_strA = strA.toCharArray();

		char[] chars_strB = strB.toCharArray();

		int m = chars_strA.length;

		int n = chars_strB.length;

		int[][] matrix = new int[m + 1][n + 1];

		for (int i = 1; i <= m; i++) {

			for (int j = 1; j <= n; j++) {

				if (chars_strA[i - 1] == chars_strB[j - 1])

					matrix[i][j] = matrix[i - 1][j - 1] + 1;

				else

					matrix[i][j] = Math.max(matrix[i][j - 1], matrix[i - 1][j]);

			}

		}

		char[] result = new char[matrix[m][n]];

		int currentIndex = result.length - 1;

		while (matrix[m][n] != 0) {

			if (matrix[n] == matrix[n - 1])

				n--;

			else if (matrix[m][n] == matrix[m - 1][n])

				m--;

			else {

				result[currentIndex] = chars_strA[m - 1];

				currentIndex--;

				n--;

				m--;

			}
		}

		return new String(result);

	}
	
	//计算两个字符串的差异值
	public static int getLevenshteinDistance(CharSequence s, CharSequence t) {
	        if (s == null || t == null) {
	           //容错，抛出的这个异常是表明在传参的时候，传递了一个不合法或不正确的参数。 好像都这样用，illegal:非法。Argument:参数，证据。
	           throw new IllegalArgumentException("Strings must not be null");
	        }
	        //计算传入的两个字符串长度
	        int n = s.length();
	        int m = t.length();
	        //容错，直接返回结果。这个处理不错
	        if (n == 0) {
	            return m;
	        } else if (m == 0) {
	            return n;
	        }
	        //这一步是根据字符串长短处理，处理后t为长字符串，s为短字符串，方便后面处理
	       if (n > m) {
	            CharSequence tmp = s;
	            s = t;
	            t = tmp;
	            n = m;
	            m = t.length();
	        }
	 
	        //开辟一个字符数组，这个n是短字符串的长度
	        int p[] = new int[n + 1];
	        int d[] = new int[n + 1];
	        //用于交换p和d的数组
	        int _d[];
	 
	        int i;
	        int j;
	        char t_j;
	        int cost;
	        //赋初值
	        for (i = 0; i <= n; i++) {
	            p[i] = i;
	        }
	 
	        for (j = 1; j <= m; j++) {
	            //t是字符串长的那个字符
	            t_j = t.charAt(j - 1);
	            d[0] = j;
	 
	            for (i = 1; i <= n; i++) {
	                //计算两个字符是否一样，一样返回0。
	                cost = s.charAt(i - 1) == t_j ? 0 : 1;
	                //可以将d的字符数组全部赋值。
	                d[i] = Math.min(Math.min(d[i - 1] + 1, p[i] + 1), p[i - 1] + cost);
	            }
	 
	            //交换p和d
	            _d = p;
	            p = d;
	            d = _d;
	        }
	         
	        //最后的一个值即为差异值
	        return p[n];
	}
}
