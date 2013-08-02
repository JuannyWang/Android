package com.cqupt.mobilestudiesdemo.media;

import java.io.Serializable;

import com.cqupt.mobilestudiesdemo.entity.ResourceEntity;
import com.cqupt.mobilestudiesdemo.util.download.DownloadJob;

public class Music  implements Serializable{
  
	private ResourceEntity resourceEntity;
	private DownloadJob downloadJob;
	
	public ResourceEntity getResourceEntity() {
		return resourceEntity;
	}
	public void setResourceEntity(ResourceEntity resourceEntity) {
		this.resourceEntity = resourceEntity;
	}
	public DownloadJob getDownloadJob() {
		return downloadJob;
	}
	public void setDownloadJob(DownloadJob downloadJob) {
		this.downloadJob = downloadJob;
	}
}
