package cn.eakay.control.parking.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.eakay.control.parking.handler.ParkingDevice;
import cn.eakay.control.parking.handler.ParkingServer;
import cn.eakay.control.parking.handler.PrakingDevicePort;
import cn.eakay.control.parking.service.ParkingControlService;

@Controller
@RequestMapping("/manager")
public class ManagerController {

	@Autowired
	private ParkingControlService carControlService;

	@RequestMapping("/index.htm")
	public String index(Model model) {
		List<ParkingServer> parkingServers = carControlService
				.queryParkingServers();
		model.addAttribute("parkingServers", parkingServers);
		return "index";
	}

	@RequestMapping("/devices.htm")
	public String devices(int serverId, Model model) {
		List<ParkingDevice> parkingDevices = carControlService
				.getParkingDevices(serverId);
		model.addAttribute("serverId", serverId);
		model.addAttribute("parkingDevices", parkingDevices);
		return "devices";
	}

	@RequestMapping("/ports.htm")
	public String ports(int serverId, String deviceKey, Model model) {
		List<PrakingDevicePort> dervicePorts = carControlService
				.getDervicePorts(serverId, deviceKey);
		model.addAttribute("dervicePorts", dervicePorts);
		model.addAttribute("deviceKey", deviceKey);
		return "ports";
	}

}
