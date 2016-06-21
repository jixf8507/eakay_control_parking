package cn.eakay.control.parking.tcpserver.netty.handler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class ParkingDeviceMessageDecoder extends
		MessageToMessageDecoder<ByteBuf> {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		System.out.println("decode=========================="+ in.capacity());
		byte[] bytes = new byte[in.capacity()];
		int length = 0 ;
		for (int i = 0; i < in.capacity(); i++) {
			length++;
			byte b = in.getByte(i);					
			if(b== (byte) 0x24){
				break ;
			}
			bytes[i] = b;	
		}
		
		byte[] array =new byte[length];
		for(int i = 0; i < length; i++){
			array[i]= bytes[i];
		}
		System.out.println("btyes=========================="+ array.length);
		out.add(array);
	}

}
