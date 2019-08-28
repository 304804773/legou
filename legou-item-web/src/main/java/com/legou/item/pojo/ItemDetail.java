package com.legou.item.pojo;

import java.util.Date;

import com.legou.pojo.TbItem;

public class ItemDetail extends TbItem {
	 private Long id;

	    private String title;

	    private String sellPoint;

	    private Long price;

	    private Integer num;

	    private String barcode;

	    private String image;

	    private Long cid;

	    private Byte status;

	    private Date created;

	    private Date updated;

		public ItemDetail(TbItem tbItem) {
			this.setId(tbItem.getId());
			this.setTitle(tbItem.getTitle());
			this.setSellPoint(tbItem.getSellPoint());
			this.setPrice(tbItem.getPrice());
			this.setNum(tbItem.getNum());
			this.setBarcode(tbItem.getBarcode());
			this.setImage(tbItem.getImage());
			this.setCid(tbItem.getCid());
			this.setStatus(tbItem.getStatus());
			this.setCreated(tbItem.getCreated());
			this.setUpdated(tbItem.getUpdated());
		}
		public String[] getImages() {
			String image2=this.getImage();
			if(image2!=null&&!"".equals(image2)) {
				String[] strings = image2.split(",");
				return strings;
			}
			
			return null;
		}


}
