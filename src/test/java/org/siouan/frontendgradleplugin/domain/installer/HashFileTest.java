package org.siouan.frontendgradleplugin.domain.installer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.siouan.frontendgradleplugin.test.fixture.PathFixture.ANY_PATH;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.siouan.frontendgradleplugin.domain.ChannelProvider;

@ExtendWith(MockitoExtension.class)
class HashFileTest {

    private static final Path FILE_PATH = ANY_PATH;

    private static final String DATA = "Lorem ipsum dolor sit amet, consectetur adipiscing elit.";

    private static final byte[] SHA256_HASH_BIN = new byte[] {(byte) 0xa5, (byte) 0x8d, (byte) 0xd8, 0x68, 0x02, 0x34,
        (byte) 0xc1, (byte) 0xf8, (byte) 0xcc, 0x2e, (byte) 0xf2, (byte) 0xb3, 0x25, (byte) 0xa4, 0x37, 0x33, 0x60,
        0x5a, 0x7f, 0x16, (byte) 0xf2, (byte) 0x88, (byte) 0xe0, 0x72, (byte) 0xde, (byte) 0x8e, (byte) 0xae,
        (byte) 0x81, (byte) 0xfd, (byte) 0x8d, 0x64, 0x33};

    private static final String SHA256_HASH_HEX = "a58dd8680234c1f8cc2ef2b325a43733605a7f16f288e072de8eae81fd8d6433";

    @Mock
    private ChannelProvider channelProvider;

    @Mock
    private ConvertToHexadecimalString convertToHexadecimalString;

    @InjectMocks
    private HashFile usecase;

    @Test
    void should_fail_when_file_cannot_be_read() throws IOException {
        final Exception expectedException = new IOException();
        when(channelProvider.getSeekableByteChannel(FILE_PATH)).thenThrow(expectedException);

        assertThatThrownBy(() -> usecase.execute(FILE_PATH)).isEqualTo(expectedException);

        verifyNoMoreInteractions(channelProvider, convertToHexadecimalString);
    }

    @Test
    void should_return_valid_sha256_hash_with_default_buffering_capacity() throws IOException {
        final SeekableByteChannel inputChannel = mock(SeekableByteChannel.class);
        when(channelProvider.getSeekableByteChannel(FILE_PATH)).thenReturn(inputChannel);
        when(inputChannel.read(any(ByteBuffer.class))).then(invocation -> {
            final ByteBuffer buffer = invocation.getArgument(0);
            final byte[] data = DATA.getBytes();
            buffer.put(data);
            return data.length;
        }).thenReturn(-1);
        when(convertToHexadecimalString.execute(SHA256_HASH_BIN)).thenReturn(SHA256_HASH_HEX);

        assertThat(usecase.execute(FILE_PATH)).isEqualTo(SHA256_HASH_HEX);

        verify(inputChannel).close();
        verifyNoMoreInteractions(inputChannel, channelProvider, convertToHexadecimalString);
    }
}
