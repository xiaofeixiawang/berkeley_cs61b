public class Solution {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		int n = in.nextInt();
		while (in.hasNext()) {
			int count = 0;
			String str1 = in.next();
			String str = str1.toLowerCase();
			int length = str.length();
			if (length % 2 == 0) {
				int half = length / 2;
				String a = str.substring(0, half);
				String b = str.substring(half, length);
				String[] a1 = a.split("");
				String[] b1 = b.split("");
				Arrays.sort(a1);
				Arrays.sort(b1);
				List s = new ArrayList(Arrays.asList(b1));
				if (Arrays.toString(a1).equals(Arrays.toString(b1))) {
					System.out.println("0");
				} else {
					for (int i = 0; i < a1.length; i++) {
						if (s.contains(a1[i])) {
							s.remove(a1[i]);
						} else {
							count++;
						}
					}
					System.out.println(count);
				}
			} else {
				System.out.println("-1");
			}
		}
	}
}