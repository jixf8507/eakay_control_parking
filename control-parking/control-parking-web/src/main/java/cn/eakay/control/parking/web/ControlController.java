package cn.eakay.control.parking.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.eakay.control.parking.service.ParkingControlService;
import cn.eakay.control.parking.tools.ControlResult;



@Controller
@RequestMapping("/control")
public class ControlController {

	@Autowired
	private ParkingControlService carControlService;

	@RequestMapping("/up.htm")
	@ResponseBody
	public ControlResult up(String deviceKey, int port) { 
		return carControlService.up(deviceKey, port);
	}

	@RequestMapping("/down.htm")
	@ResponseBody
	public ControlResult down(String deviceKey, int port) {
		return carControlService.down(deviceKey, port);
	}

	@RequestMapping("/query.htm")
	@ResponseBody
	public ControlResult query(String deviceKey, int port) {
		return carControlService.query(deviceKey, port);
	}

}
