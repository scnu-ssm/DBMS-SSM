package com.zichang.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.scnu.dao.TableInfoMapper;
import com.zichang.bean.Fields;
import com.zichang.bean.ForeignKey;
import com.zichang.bean.Indexes;

public class TableInfoTool {
	
	
	public static List<Fields> getFieldList(JSONArray jsonArray){
		List<Fields> fields = new ArrayList<Fields>();
		for(int i=0; i<jsonArray.size(); i++) {
			Fields f = new Fields();
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			f.setName(jsonObject.getString("name"));
			f.setLength(jsonObject.getString("length"));
			f.setType(jsonObject.getString("type"));
			f.setNotNull(jsonObject.getIntValue("notNull"));
			f.setPrimary(jsonObject.getIntValue("primary"));
			fields.add(f);
		}
		return fields;
	}

	public static List<Indexes> getIndexesList(JSONArray jsonArray){
		List<Indexes> indexes = new ArrayList<Indexes>();
		for(int i = 0; i<jsonArray.size(); i++) {
			Indexes in = new Indexes();
			JSONObject jsonObject = jsonArray.getJSONObject(i);
			in.setName(jsonObject.getString("name"));
			in.setField(jsonObject.getString("field"));
			in.setType(jsonObject.getString("type"));
			in.setMethod(jsonObject.getString("method"));
			indexes.add(in);
		}
		return indexes;
	}
	
	public static List<ForeignKey> getForeignkeyList(JSONArray jsonArray){
		List<ForeignKey> foreignKeys = new ArrayList<ForeignKey>();
		if(jsonArray != null) {
			for(int i=0; i<jsonArray.size(); i++) {
				ForeignKey f = new ForeignKey();
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				f.setName(jsonObject.getString("name"));
				f.setField(jsonObject.getString("field"));
				f.setWithDatabase(jsonObject.getString("withDatabase"));
				f.setWithTable(jsonObject.getString("withTable"));
				f.setWithField(jsonObject.getString("withField"));
				f.setDelete(jsonObject.getString("delete"));
				f.setUpdate(jsonObject.getString("update"));
				foreignKeys.add(f);
			}
		}
		return foreignKeys;
	}
	
}
