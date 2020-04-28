package org.siouan.frontendgradleplugin.infrastructure.provider;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.io.InputStream;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;

import org.junit.jupiter.api.Test;

class ChannelProviderImplTest {

	@Test
	void shouldNotSetAuthorizationRequestHeaderWhenAuthorizationHeaderIsNull() throws Exception {
		ChannelProviderImpl channelProvider = new ChannelProviderImpl();
		URL url = mock(URL.class);
		URLConnection urlConnection = mock(URLConnection.class);
		doReturn(urlConnection).when(url).openConnection(any());
		doReturn(mock(InputStream.class)).when(urlConnection).getInputStream();

		channelProvider.getReadableByteChannel(url, Proxy.NO_PROXY, null);

		verify(urlConnection, never()).setRequestProperty(any(), any());
	}

	@Test
	void shouldSetAuthorizationRequestHeaderWhenAuthorizationHeaderNotNull() throws Exception {
		ChannelProviderImpl channelProvider = new ChannelProviderImpl();
		URL url = mock(URL.class);
		URLConnection urlConnection = mock(URLConnection.class);
		doReturn(urlConnection).when(url).openConnection(any());
		doReturn(mock(InputStream.class)).when(urlConnection).getInputStream();

		channelProvider.getReadableByteChannel(url, Proxy.NO_PROXY, "foo");

		verify(urlConnection).setRequestProperty(eq("Authorization"), eq("foo"));
	}

}
