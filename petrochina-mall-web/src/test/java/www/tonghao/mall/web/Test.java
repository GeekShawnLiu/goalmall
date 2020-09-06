package www.tonghao.mall.web;

public class Test {

	public static void main(String[] args) {
		activity:for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 4; j++) {
				System.out.println("w内"+j);
				if(j == 2){
					break activity;
				}
			}
			System.out.println("w外"+i);
			if (i == 3){
				break activity;
			}
		}
		
		System.out.println("结束");
	}
}
