package me.monster.blogtest;

import org.junit.Test;

/**
 * @description
 * @author: Created jiangjiwei in 2022/2/12 3:54 下午
 */
public class StatusCodeTest {

    private static String toBinary(int num) {
        // 4 位
        int value = 1 << 4 | num;
        String bs = Integer.toBinaryString(value); //0x20 | 这个是为了保证这个string长度是6位数
        return " "/* + num + " "  */+bs.substring(1);
    }

    @Test
    public void test_1_and() {
        int a = 0x0001;
        int b = 0x0002;
        int c = 0x0004;
        System.out.println("a = 0x0001 " + a + " ，二进制 " + toBinary(a));
        System.out.println("b = 0x0002 " + b + " ，二进制 " + toBinary(b));
        System.out.println("c = 0x0004 " + c + " ，二进制 " + toBinary(c));
//        常规四则运算
        System.out.println("a + b = " + (a + b));
        System.out.println("a - b = " + (a - b));
        System.out.println("a * b = " + (a * b));
        System.out.println("a / b = " + (a / b));
        // 位运算
        // 与运算，二进制的每一位进行与操作，均为 1 结果才是 1
        System.out.println("a & b = " + (a & b));
        // 或运算，二进制的每一位进行或操作，均为 0 结果才是 0
        System.out.println("a | b = " + (a | b));

        // 添加状态位：用或运算 |
        // 判断状态是否存在：用与运用 & 并判断结果是否为 0，0 没有此状态，有值为有此状态
        // 移除状态位：用 与运算的取反，对移除的状态取反，然后和状态集进行 与运算，相当于 0111 对 0001 的反值（0000）与运算 就成了 0110
        int result = a | b | c;
        System.out.println("a | b | c = " + result + " " + toBinary(result));

        int resultB = result & b;
        System.out.println("result 是否包含 b " + (resultB != 0) + " " + toBinary(resultB));

        result = result & ~b;
        System.out.println("result & ~b " + result + " " + toBinary(result));

        resultB = result & b;
        System.out.println("result 是否包含 b " + (resultB != 0) + " " + toBinary(resultB));
    }

    @Test
    public void countTrue() {
        System.out.println("1<<1" + (toBinary(1 << 1)));
        System.out.println("1<<2" + (toBinary(1 << 2)));
        System.out.println("1<<3" + (toBinary(1 << 3)));
        System.out.println("1<<4" + (toBinary(1 << 4)));
        System.out.println("1<<5" + (toBinary(1 << 5)));
        System.out.println("1<<6" + (toBinary(1 << 6)));
        System.out.println("1<<7" + (toBinary(1 << 7)));
        System.out.println("1<<8" + (toBinary(1 << 8)));
        System.out.println("1<<9" + (toBinary(1 << 9)));
//        System.out.println("1<<10" + (toBinary(1 << 10)));
    }
}
