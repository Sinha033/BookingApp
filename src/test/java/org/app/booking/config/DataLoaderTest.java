package org.app.booking.config;

import org.app.booking.repository.MovieRepository;
import org.app.booking.repository.SeatRepository;
import org.app.booking.repository.ShowRepository;
import org.app.booking.repository.TheatreRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataLoaderTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private TheatreRepository theatreRepository;

    @Mock
    private ShowRepository showRepository;

    @Mock
    private SeatRepository seatRepository;

    @InjectMocks
    private DataLoader dataLoader;

    @Test
    void loadData_shouldSaveInitialData() {

        when(movieRepository.count()).thenReturn(0L);

        dataLoader.loadData();

        verify(movieRepository, times(1)).saveAll(anyList());
        verify(theatreRepository, times(1)).saveAll(anyList());
        verify(showRepository, times(1)).saveAll(anyList());
        verify(seatRepository, times(100)).save(any());
    }
}