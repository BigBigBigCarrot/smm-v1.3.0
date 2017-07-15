package com.david.test.solr;

import java.io.IOException;

import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.junit.Test;

public class SolrjTest {
	
	@Test
	public void test2(){
		String urlString = "http://localhost:8983/solr/david";
		SolrClient solr = new HttpSolrClient.Builder(urlString).build();
//		solr.setParser(new XMLResponseParser());

		SolrQuery query = new SolrQuery();
//		String mQueryString="first_name:j*";
//		query.setQuery(mQueryString);
//		query.setRequestHandler("/spellCheckCompRH");
		
		//You can also set arbitrary parameters on the query object.  
		//The first two code lines below are equivalent to each other, and the third shows how to use an arbitrary parameter q to set the query string:
//		query.set("fl", "category,title,price");
//		query.setFields("category", "title", "price");
		query.set("q", "first_name:j*");
		QueryResponse response;
		try{
			response = solr.query(query);	
		}catch(IOException e){
			System.out.println(e.toString());
			return;
		}catch (SolrServerException e) {
			System.out.println(e.toString());
			return;
		}
		
	}

	public static void main(String[] args) {
	}
}
