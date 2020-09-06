package www.tonghao.common.utils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value="分页参数对象",description="分页参数对象")
public class PageBean implements Serializable {
   @ApiModelProperty(value="当前页",name="page")
   private Integer page;
   
   @ApiModelProperty(value="一页显示的条数",name="rows")
   private Integer rows;
   
  public Integer getPage() {
    return page;
  }
  public void setPage(Integer page) {
    this.page = page;
  }
  public Integer getRows() {
    return rows;
  }
  public void setRows(Integer rows) {
    this.rows = rows;
  }
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((page == null) ? 0 : page.hashCode());
    result = prime * result + ((rows == null) ? 0 : rows.hashCode());
    return result;
  }
  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    PageBean other = (PageBean) obj;
    if (page == null) {
      if (other.page != null)
        return false;
    } else if (!page.equals(other.page))
      return false;
    if (rows == null) {
      if (other.rows != null)
        return false;
    } else if (!rows.equals(other.rows))
      return false;
    return true;
  }
   
}
