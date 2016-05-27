package com.sist.emotion;

import java.util.ArrayList;
import java.util.List;

import org.bson.NewBSONDecoder;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

@Repository
public class EmotionDAO {
	private MongoClient mc;
	private DB db;
	 private DBCollection dbc;
	 
	 public EmotionDAO()
	 {
		 try
		 {
			 mc=new MongoClient("localhost");
			 db=mc.getDB("mydb");
			 dbc=db.getCollection("emotion");
		 }catch(Exception ex)
		 {
			 System.out.println(ex.getMessage());
		 }
	 }
	 
	 
	public void EmotionInsert(EmotionVO vo)
	{
		try
		{
			DBCursor cursor=dbc.find();
			
			while(cursor.hasNext())
			{
				BasicDBObject obj=(BasicDBObject)cursor.next();
				String emotion=obj.getString("emotion");
				int ecount=obj.getInt("ecount");
			}
			cursor.close();
			
			BasicDBObject in=new BasicDBObject();
			in.put("emotion",vo.getEmotion());
			in.put("ecount", vo.getEcount());
			
			BasicDBObject where=new BasicDBObject();
			where.put("emotion", vo.getEmotion());
		
			dbc.insert(in);
			
		}catch(Exception ex)
		{
			System.out.println("EmotionInsert(): "+ex.getMessage());
		}
	}
	
	
	
	public List<EmotionVO> EmotionAllData(String emotion)
	{
		List<EmotionVO> list=new ArrayList<EmotionVO>();
		try
		{
			DBCursor cursor=dbc.find();
			while(cursor.hasNext())
			{
				BasicDBObject obj=(BasicDBObject)cursor.next();
				if(emotion.equals(obj.getString("emotion")))
				{
					EmotionVO vo=new EmotionVO();
					vo.setEmotion(obj.getString(emotion));
					vo.setEcount(obj.getInt("ecount"));
					list.add(vo);
				}
			}
			cursor.close();
		}catch(Exception ex)
		{
			System.out.println("EmotionAllData(): "+ex.getMessage());
		}
		return list;
	}
	
	
	public EmotionVO EmotionClickData(String  emotion)
	{
		EmotionVO vo=new EmotionVO();
		try
		{
			BasicDBObject where=new BasicDBObject();
			where.put("emotion",emotion);
			
			BasicDBObject data1=(BasicDBObject)dbc.findOne(where);
			int ecount=data1.getInt("ecount");
			
			BasicDBObject up=new BasicDBObject();
			up.put("ecount", ecount+1);
			dbc.update(where, new BasicDBObject("$set",up));
			
			BasicDBObject data=(BasicDBObject)dbc.findOne(where);
			vo.setEmotion(data.getString("emotion"));
			vo.setEcount(data.getInt("ecount"));
		}catch(Exception ex)
		{
			System.out.println("EmotionClickData(): "+ex.getMessage());
		}
		return vo;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}