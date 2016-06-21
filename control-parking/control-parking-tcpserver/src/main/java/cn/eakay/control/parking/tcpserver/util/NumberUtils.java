package cn.eakay.control.parking.tcpserver.util;

public class NumberUtils {
	public byte unsigned(short s) {
		return (byte)s;
	}
	
	public static byte[] int2Byte(int intValue) {
        byte[] b = new byte[4];
        for (int i = 0; i < 4; i++) {
            b[i] = (byte) (intValue >> 8 * (3 - i) & 0xFF);
            System.err.print("b["+i+"]"+Integer.toBinaryString(b[i])+" ");
           
        }
        return b;
    }
	
	public static void main(String[] arg){
		byte[] bs = int2Byte(3);
		for(int i=0;i<bs.length;i++){
			System.out.println(bs[i]);
		}
	}
}
