package cn.eakay.control.parking.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.eakay.control.parking.service.CustomerParkingControlService;
import cn.eakay.control.parking.tools.ControlResult;
import cn.eakay.control.parking.tools.MSG;
import cn.eakay.control.parking.util.AuthUtil;
import cn.eakay.control.parking.util.URLUtil;

@Controller
@RequestMapping("/customer/control")
public class CustomerControlApiController {

	@Autowired
	private CustomerParkingControlService controlService;

	@RequestMapping("/parkingStart.htm")
	@ResponseBody
	public ControlResult parkingStart(HttpServletRequest request) {
		MSG msg = AuthUtil.verifySign(URLUtil.extractParam(request), true);
		if (msg.isSuccess()) {
			try {
				int orderId = Integer.parseInt(request.getParameter("orderId"));
				int customerId = Integer.parseInt(request
						.getParameter("customerId"));
				return controlService.parkingStart(orderId, customerId);
			} catch (Exception e) {
				msg.setSuccess(false);
				msg.setInfo("应用参数缺失或错误！");
			}
		}
		return ControlResult.create(msg, "");
	}

	@RequestMapping("/parkingEnd.htm")
	@ResponseBody
	public ControlResult parkingEnd(HttpServletRequest request) {
		MSG msg = AuthUtil.verifySign(URLUtil.extractParam(request), true);
		if (msg.isSuccess()) {
			try {
				int orderId = Integer.parseInt(request.getParameter("orderId"));
				int customerId = Integer.parseInt(request
						.getParameter("customerId"));
				return controlService.parkingEnd(orderId, customerId);
			} catch (Exception e) {
				msg.setSuccess(false);
				msg.setInfo("应用参数缺失或错误！");
			}
		}
		return ControlResult.create(msg, "");
	}

	@RequestMapping("/up.htm")
	@ResponseBody
	public ControlResult up(HttpServletRequest request) {
		MSG msg = AuthUtil.verifySign(URLUtil.extractParam(request), true);
		if (msg.isSuccess()) {
			try {
				int orderId = Integer.parseInt(request.getParameter("orderId"));
				int customerId = Integer.parseInt(request
						.getParameter("customerId"));
				return controlService.up(orderId, customerId);
			} catch (Exception e) {
				msg.setSuccess(false);
				msg.setInfo("应用参数缺失或错误！");
			}
		}
		return ControlResult.create(msg, "");
	}

	@RequestMapping("/down.htm")
	@ResponseBody
	public ControlResult down(HttpServletRequest request) {
		MSG msg = AuthUtil.verifySign(URLUtil.extractParam(request), true);
		if (msg.isSuccess()) {
			try {
				int orderId = Integer.parseInt(request.getParameter("orderId"));
				int customerId = Integer.parseInt(request
						.getParameter("customerId"));
				return controlService.down(orderId, customerId);
			} catch (Exception e) {
				msg.setSuccess(false);
				msg.setInfo("应用参数缺失或错误！");
			}
		}
		return ControlResult.create(msg, "");
	}

}
