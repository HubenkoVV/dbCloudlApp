package ua.training.model.service;

import org.junit.Test;
import ua.training.model.dao.PeriodicalDao;
import ua.training.model.dao.factory.DaoFactory;
import ua.training.model.entities.Periodical;
import ua.training.model.service.exception.IncorrectDataException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

public class PeriodicalServiceTest {

    private DaoFactory daoFactoryMock = mock(DaoFactory.class);
    private PeriodicalDao periodicalDaoMock = mock(PeriodicalDao.class);
    
    @Test
    public void createPeriodical() throws Exception {
        Periodical periodical = new Periodical.PeriodicalBuilder()
                .buildId(1)
                .buildShortDescription("test")
                .buildPrice(0)
                .buildName("test")
                .buildLazy();
        Periodical failedPeriodical = new Periodical.PeriodicalBuilder()
                .buildId(2)
                .buildShortDescription("")
                .buildPrice(0)
                .buildName("")
                .buildLazy();

        when(daoFactoryMock.createPeriodicalDao()).thenReturn(periodicalDaoMock);
        when(periodicalDaoMock.create(periodical)).thenReturn(1);
        when(periodicalDaoMock.create(failedPeriodical)).thenReturn(0);

        PeriodicalService periodicalService = new PeriodicalService();
        periodicalService.daoFactory = daoFactoryMock;
        Periodical resultPeriodical = periodicalService.createPeriodical(periodical);
        try {
            periodicalService.createPeriodical(failedPeriodical);
        } catch (IncorrectDataException ignored){}

        verify(daoFactoryMock,times(2)).createPeriodicalDao();
        verify(periodicalDaoMock).create(periodical);
        verify(periodicalDaoMock).create(failedPeriodical);
        verify(periodicalDaoMock, times(2)).close();

        assertThat(resultPeriodical, is(periodical));
    }

    @Test
    public void getById() throws Exception {
        int id = 1;
        Periodical periodical = new Periodical.PeriodicalBuilder()
                .buildId(id)
                .buildLazy();

        when(daoFactoryMock.createPeriodicalDao()).thenReturn(periodicalDaoMock);
        when(periodicalDaoMock.findById(id)).thenReturn(periodical);

        PeriodicalService periodicalService = new PeriodicalService();
        periodicalService.daoFactory = daoFactoryMock;
        Periodical resultPeriodical = periodicalService.getById(id);

        verify(daoFactoryMock).createPeriodicalDao();
        verify(periodicalDaoMock).findById(id);
        verify(periodicalDaoMock).close();

        assertThat(resultPeriodical, is(periodical));
    }

    @Test
    public void getPeriodicalsOnPages() throws Exception {
        Map<Integer, List<Periodical>> listPeriodicals = new HashMap<>();
        listPeriodicals.put(1, new ArrayList<Periodical>(){{
            add(new Periodical.PeriodicalBuilder().buildId(1).buildLazy());
            add(new Periodical.PeriodicalBuilder().buildId(2).buildLazy());
            add(new Periodical.PeriodicalBuilder().buildId(3).buildLazy());
            add(new Periodical.PeriodicalBuilder().buildId(4).buildLazy());}});
        listPeriodicals.put(2, new ArrayList<Periodical>(){{
            add(new Periodical.PeriodicalBuilder().buildId(5).buildLazy());
            add(new Periodical.PeriodicalBuilder().buildId(6).buildLazy());}});

        when(daoFactoryMock.createPeriodicalDao()).thenReturn(periodicalDaoMock);
        when(periodicalDaoMock.findFixedNumberOfPeriodicals(4,0)).thenReturn(listPeriodicals.get(1));
        when(periodicalDaoMock.findFixedNumberOfPeriodicals(4,4)).thenReturn(listPeriodicals.get(2));

        PeriodicalService periodicalService = new PeriodicalService();
        periodicalService.daoFactory = daoFactoryMock;
        Map<Integer, List<Periodical>> resultListPeriodicals = periodicalService.getPeriodicalsOnPages(4);

        verify(daoFactoryMock).createPeriodicalDao();
        verify(periodicalDaoMock).findFixedNumberOfPeriodicals(4,0);
        verify(periodicalDaoMock).findFixedNumberOfPeriodicals(4,4);

        assertThat(resultListPeriodicals, is(listPeriodicals));
    }

    @Test
    public void searchPeriodicals() throws Exception {
        String search = "Per";
        Map<Integer, List<Periodical>> listPeriodicals = new HashMap<>();
        listPeriodicals.put(1, new ArrayList<Periodical>(){{
            add(new Periodical.PeriodicalBuilder().buildId(1).buildLazy());
            add(new Periodical.PeriodicalBuilder().buildId(2).buildLazy());
            add(new Periodical.PeriodicalBuilder().buildId(3).buildLazy());
            add(new Periodical.PeriodicalBuilder().buildId(4).buildLazy());}});
        listPeriodicals.put(2, new ArrayList<Periodical>(){{
            add(new Periodical.PeriodicalBuilder().buildId(5).buildLazy());
            add(new Periodical.PeriodicalBuilder().buildId(6).buildLazy());}});

        when(daoFactoryMock.createPeriodicalDao()).thenReturn(periodicalDaoMock);
        when(periodicalDaoMock.searchPeriodicals(search,4,0)).thenReturn(listPeriodicals.get(1));
        when(periodicalDaoMock.searchPeriodicals(search,4,4)).thenReturn(listPeriodicals.get(2));

        PeriodicalService periodicalService = new PeriodicalService();
        periodicalService.daoFactory = daoFactoryMock;
        Map<Integer, List<Periodical>> resultListPeriodicals = periodicalService.searchPeriodicals(search,4);

        verify(daoFactoryMock).createPeriodicalDao();
        verify(periodicalDaoMock).searchPeriodicals(search,4,0);
        verify(periodicalDaoMock).searchPeriodicals(search,4,4);

        assertThat(resultListPeriodicals, is(listPeriodicals));
    }

}