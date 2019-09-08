package com.imall.vo;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import com.imall.common.pojo.PageResult;
import com.imall.pojo.Brand;
import com.imall.pojo.Category;

public class SearchResult<Goods> extends PageResult<Goods> {

    private List<Brand> brands;
    private List<Category> categories;
    private List<Map<String, Object>> specs;
    

    public SearchResult(long total, long totalPage, List<Goods> items, List<Brand> brands, List<Category> categories,
			List<Map<String, Object>> specs) {
		super(total, totalPage, items);
		this.brands = brands;
		this.categories = categories;
		this.specs = specs;
	}

	public List<Brand> getBrands() {
        return brands;
    }

    public void setBrands(List<Brand> brands) {
        this.brands = brands;
    }

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}

	public void setSpecs(List<Map<String, Object>> specs) {
		this.specs = specs;
	}
	public List<Map<String, Object>> getSpecs() {
		return specs;
	}
   

  
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;
//        SearchResult<?> that = (SearchResult<?>) o;
//        return Objects.equals(brands, that.brands) &&
//                Objects.equals(categories, that.categories) &&
//                Objects.equals(specs, that.specs);
//    }

    
}
