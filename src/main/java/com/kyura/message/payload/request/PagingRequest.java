package com.kyura.message.payload.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.apache.http.annotation.Obsolete;
import org.springframework.data.domain.Sort;
import org.springframework.util.CollectionUtils;

import javax.persistence.criteria.Order;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
public class PagingRequest {
	private Integer page = 1;
	private Integer size = 20;
	private List<String> sort;
	private String searchText;
}
