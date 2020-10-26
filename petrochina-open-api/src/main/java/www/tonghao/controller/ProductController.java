package www.tonghao.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import www.tonghao.service.ProductApiService;
import www.tonghao.util.ApiResultUtil;
import www.tonghao.util.ApiParamCheckUtil;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    @Autowired
    private ProductApiService productApiService;


    /**
     * 获取品目池
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/get_pools", method = RequestMethod.POST)
    public String getPools(HttpServletRequest request) {
        String check = ApiParamCheckUtil.check(request, false, false);
        if (check != null) {
            return ApiResultUtil.error(check);
        }
        return productApiService.getPools(request.getParameter("platformCode"));
    }

    /**
     * 获取商品sku
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/skus", method = RequestMethod.POST)
    public String skus(HttpServletRequest request) {
        String check = ApiParamCheckUtil.check(request, false, false);
        if (check != null) {
            return ApiResultUtil.error(check);
        }
        String catalogId = request.getParameter("catalog_id");
        String platformCode = request.getParameter("platformCode");
        return productApiService.skus(catalogId, platformCode);
    }

    /**
     * 商品详情
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/detail", method = RequestMethod.POST)
    public String detail(HttpServletRequest request) {
        String check = ApiParamCheckUtil.check(request, true, false);
        if (check != null) {
            return ApiResultUtil.error(check);
        }
        String platformCode = request.getParameter("platformCode");
        String sku = request.getParameter("sku");
        String productExtendAttributes = request.getParameter("product_extend_attributes");
        return productApiService.detail(sku, productExtendAttributes, platformCode);
    }

    /**
     * 上下架状态
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/shelf_states", method = RequestMethod.POST)
    public String shelfStates(HttpServletRequest request) {
        String check = ApiParamCheckUtil.check(request, true, false);
        if (check != null) {
            return ApiResultUtil.error(check);
        }
        String platformCode = request.getParameter("platformCode");
        String sku = request.getParameter("sku");
        return productApiService.shelfStates(sku, platformCode);
    }

    /**
     * 商品图片
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/images", method = RequestMethod.POST)
    public String images(HttpServletRequest request) {
        String check = ApiParamCheckUtil.check(request, true, false);
        if (check != null) {
            return ApiResultUtil.error(check);
        }
        String platformCode = request.getParameter("platformCode");
        String sku = request.getParameter("sku");
        return productApiService.images(sku, platformCode);
    }

    /**
     * 价格
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/prices", method = RequestMethod.POST)
    public String prices(HttpServletRequest request) {
        String check = ApiParamCheckUtil.check(request, true, false);
        if (check != null) {
            return ApiResultUtil.error(check);
        }
        String platformCode = request.getParameter("platformCode");
        String sku = request.getParameter("sku");
        return productApiService.prices(sku, platformCode);
    }

    /**
     * 库存
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/stocks", method = RequestMethod.POST)
    public String stocks(HttpServletRequest request) {
        String check = ApiParamCheckUtil.check(request, true, false);
        if (check != null) {
            return ApiResultUtil.error(check);
        }
        String platformCode = request.getParameter("platformCode");
        String sku = request.getParameter("sku");
        String area = request.getParameter("area");
        if (StringUtils.isBlank(area)) {
            return ApiResultUtil.error("地址信息不能为空");
        }
        return productApiService.stocks(sku, area, platformCode);
    }

    /**
     * 获取消息
     */
    @RequestMapping(value = "/getMessagePool", method = RequestMethod.POST)
    public String getMessagePool(HttpServletRequest request) {
        String check = ApiParamCheckUtil.check(request, false, false);
        if (check != null) {
            return ApiResultUtil.error(check);
        }
        String platformCode = request.getParameter("platformCode");
        String type = request.getParameter("type");
        if (StringUtils.isBlank(type)) {
            return ApiResultUtil.error("类型不能为空");
        }
        return productApiService.getMessagePool(type, platformCode);
    }

    /**
     * 删除消息
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/delMessagePool", method = RequestMethod.POST)
    public String delMessagePool(HttpServletRequest request) {
        String check = ApiParamCheckUtil.check(request, false, false);
        if (check != null) {
            return ApiResultUtil.error(check);
        }
        String platformCode = request.getParameter("platformCode");
        String messageId = request.getParameter("messageId");
        if (StringUtils.isBlank(messageId)) {
            return ApiResultUtil.error("messageId不能为空");
        }
        return productApiService.delMessagePool(Long.parseLong(messageId), platformCode);
    }

    /**
     * 节能环保接口
     *
     * @return
     */
    @RequestMapping(value = "/certificates", method = RequestMethod.POST)
    public String certificates(HttpServletRequest request) {
        String check = ApiParamCheckUtil.check(request, true, false);
        if (check != null) {
            return ApiResultUtil.error(check);
        }
        String platformCode = request.getParameter("platformCode");
        String skus = request.getParameter("sku");
        return productApiService.certificates(skus, platformCode);
    }
}
