///////////////////////////////////////////////////////////////////////////////////////////// Test
//기본 제공코드는 임의 수정해도 관계 없습니다. 단, 입출력 포맷 주의
//아래 표준 입출력 예제 필요시 참고하세요.
//표준 입력 예제
//int a = sc.nextInt();                 // int 변수 1개 입력받는 예제
//double b = sc.nextDouble();           // double 변수 1개 입력받는 예제
//long AB = sc.nextLong();              // long 변수 1개 입력받는 예제
//String str = sc.next();               // 문자열 1개 입력받는 예제
//char c = str.charAt(0);               // 입력받은 문자열에서 char 변수 1개 가져오는 예제
/////////////////////////////////////////////////////////////////////////////////////////////
//표준 출력 예제
//int a = 0;
//double b = 1.0;
//long AB = 12345678901234567L;
//String str = "ABCDEFG";
//char c = 'b';
//System.out.println(a);                // int 변수 1개 출력하는 예제
//System.out.println(b);                // double 변수 1개 출력하는 예제
//System.out.println(AB);               // long 변수 1개 출력하는 예제
//System.out.println(str);              // 문자열 1개 출력하는 예제
//System.out.println(c);                // char 변수 1개 출력하는 예제
/////////////////////////////////////////////////////////////////////////////////////////////

import java.util.*;

/*
   사용하는 클래스명이 Solution 이어야 하므로, 가급적 Solution.java 를 사용할 것을 권장합니다.
   이러한 상황에서도 동일하게 java Solution 명령으로 프로그램을 수행해볼 수 있습니다.
*/
public class Algorithm
{
    public static void main(String args[]) throws Exception
    {
		/*
		   아래의 메소드 호출은 앞으로 표준 입력(키보드) 대신 sample_input.txt 파일로부터 읽어오겠다는 의미의 코드입니다.
		   여러분이 작성한 코드를 테스트 할 때, 편의를 위해서 sample_input.txt에 입력을 저장한 후,
		   이 코드를 프로그램의 처음 부분에 추가하면 이후 입력을 수행할 때 표준 입력 대신 파일로부터 입력을 받아올 수 있습니다.
		   따라서 테스트를 수행할 때에는 아래 주석을 지우고 이 메소드를 사용하셔도 좋습니다.
		   단, 채점을 위해 코드를 제출하실 때에는 반드시 이 메소드를 지우거나 주석 처리 하셔야 합니다.
		*/
        System.setIn(new java.io.FileInputStream("C:\\Users\\KH\\Downloads\\sol2input.txt"));

		/*
		   표준입력 System.in 으로부터 스캐너를 만들어 데이터를 읽어옵니다.
		*/
        Scanner sc = new Scanner(System.in);

		/*
		   여러 개의 테스트 케이스가 주어지므로, 각각을 처리합니다.
		*/
        int T = sc.nextInt();




        for (int test_case = 1; test_case <= T; test_case++)
        {
            /////////////////////////////////////////////////////////////////////////////////////////////

			   //이 부분에 여러분의 알고리즘 구현이 들어갑니다.

            /////////////////////////////////////////////////////////////////////////////////////////////
            int count = 0;
            int flag = 0; // 짝수일때 0
            int N = sc.nextInt();
            if(N == 1) {
                System.out.println("#" + test_case + " " + count);
                sc.nextInt();
                continue;
            } else {
                ArrayList<Integer> arraylist = new ArrayList<>();
                for(int i = 0; i < N; i++) {
                    arraylist.add(sc.nextInt());
                }
                for(int i = 0; i < N - 1; i++) {
                    int temp = 0; // 교환 전 임시변수
                    if (isEven(arraylist.get(i)) ^ isEven(arraylist.get(i + 1)))  {
                        continue;
                    }
                    for(int j = i + 2; j < N; j++) {
                        // 하나는 true, 하나는 false일때가 조건맞는것임
                        if (isEven(arraylist.get(i)) ^ isEven(arraylist.get(j))) {
                            temp = arraylist.get(i + 1);
                            arraylist.set(i + 1, arraylist.get(j));
                            arraylist.set(j, temp);
                            count++;
                            break;
                        }
                    }
                }
                System.out.println("#" + test_case + " " + arraylist + "count : " + count);
            }
            if(count == 0) {
                System.out.println("#" + test_case + " " + -1);
            } else {

                // 표준출력(화면)으로 답안을 출력합니다.
                System.out.println("#" + test_case + " " + count);
            }
        }

        sc.close(); // 사용이 끝난 스캐너 객체를 닫습니다.
    }
    // 짝수인지?
    public static boolean isEven (int num) {
        if(num % 2 == 0) {
            return true;
        } else {
            return false;
        }
    }
}