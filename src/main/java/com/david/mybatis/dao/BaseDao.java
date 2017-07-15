package com.david.mybatis.dao;

import java.io.Serializable;
import java.util.List;

public abstract interface BaseDao<T, ID extends Serializable>
{
	/*
  public abstract int insert(T paramT);
  
  public abstract int update(T paramT);
  
  public abstract int delete(ID paramID);
  
  public abstract int deletes(List<String> paramList);
  */
  public abstract T get(ID paramID);
}
