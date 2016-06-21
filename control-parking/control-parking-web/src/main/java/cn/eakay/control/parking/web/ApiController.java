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
@RequestMapping("")
public class ApiController {

	@Autowired
	private CustomerParkingControlService controlService;

	@RequestMapping("/parkingControl.htm")
	@ResponseBody
	public ControlResult parkingControl(HttpServletRequest request) {
		MSG msg = AuthUtil.verifySign(URLUtil.extractParam(request), true);
		if (msg.isSuccess()) {
			try {
				int orderId = Integer.parseInt(request
						.getParameter("BookingNo"));
				int customerId = Integer.parseInt(request
						.getParameter("customerID"));
				String type = request.getParameter("CtrlOrderType");
				String ctrlTag = request.getParameter("ctrlTag");
				if ("3".equals(type)) {
					if ("1".equals(ctrlTag)) {
						return controlService.parkingStart(orderId, customerId);
					}
					if ("0".equals(ctrlTag)) {
						return controlService.up(orderId, customerId);
					}
				}
				if ("4".equals(type)) {
					if ("1".equals(ctrlTag)) {
						return controlService.parkingEnd(orderId, customerId);
					}
					if ("0".equals(ctrlTag)) {
						return controlService.down(orderId, customerId);
					}
				}
			} catch (Exception e) {
				msg.setSuccess(false);
				msg.setInfo("应用参数缺失或错误！");
			}
		}
		return ControlResult.create(msg, "");
	}

}
