package com.hanshardmeier.main;

import java.io.IOException;
import java.net.URL;
import java.security.GeneralSecurityException;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential.Builder;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gdata.client.contacts.ContactsService;
import com.google.gdata.data.contacts.ContactGroupEntry;
import com.google.gdata.data.contacts.ContactGroupFeed;

public class GoogleConnection {
	
	private ContactsService mContactsService;

	public GoogleConnection(String REFRESH_TOKEN, String ACCESS_TOKEN)
			throws GeneralSecurityException, IOException {
		String CLIENT_ID = "971138936073-73tr9g5o0ddc73d7i1o4flcn9bqgj4j7.apps.googleusercontent.com";
		String CLIENT_SECRET = "ag0hNWCli2fEw5P2nECk84nQ";

		Builder builder = new GoogleCredential.Builder();
		builder.setTransport(GoogleNetHttpTransport.newTrustedTransport());
		builder.setJsonFactory(JacksonFactory.getDefaultInstance());
		builder.setClientSecrets(CLIENT_ID, CLIENT_SECRET);

		Credential credential = builder.build();
		credential.setRefreshToken(REFRESH_TOKEN);
		credential.setAccessToken(ACCESS_TOKEN);

		mContactsService = new ContactsService("GoogleGroupDelete");
		mContactsService.setOAuth2Credentials(credential);
	}

	public int deleteAllGroups() throws Exception {
		if (mContactsService == null)
			throw new Exception("ContactsService is not initialized");
		// Request the feed
		URL feedUrl = new URL("https://www.google.com/m8/feeds/groups/default/full");
		ContactGroupFeed resultFeed = mContactsService.getFeed(feedUrl, ContactGroupFeed.class);
		int groupCounter = 0;
		for (ContactGroupEntry groupEntry : resultFeed.getEntries()) {
			System.out.println("Atom Id: " + groupEntry.getId());
			System.out.println("Group Name: " + groupEntry.getTitle().getPlainText());
			System.out.println("Last Updated: " + groupEntry.getUpdated());

			URL contactGroupURL = new URL(groupEntry.getId());
			ContactGroupEntry group = mContactsService.getEntry(contactGroupURL, ContactGroupEntry.class);

			if (group.getTitle().getPlainText().startsWith("System Group")) {
				System.out.println("Skiping Group. It can not be deleted");
			} else {
				group.delete();
				groupCounter++;
			}

		}
		return groupCounter;
	}

}
