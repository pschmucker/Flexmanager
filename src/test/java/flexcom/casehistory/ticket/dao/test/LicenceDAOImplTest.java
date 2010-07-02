package flexcom.casehistory.ticket.dao.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import flexcom.casehistory.data.Data;
import flexcom.casehistory.data.LicenceDataSet;
import flexcom.casehistory.ticket.dao.LicenceDAO;
import flexcom.casehistory.ticket.dao.LicenceDAOImpl;
import flexcom.casehistory.ticket.entity.Licence;
import flexcom.casehistory.ticket.search.Query;

/**
 * Test class for {@link LicenceDAOImpl}
 * @author philippe
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = "classpath:applicationContext.xml")
public class LicenceDAOImplTest {
	
	/**
	 * ID for the {@link Licence}
	 */
	private long licenceId;
	
	@Autowired
	private LicenceDAO licenceDAO;

	@Autowired
	private LicenceDataSet licenceDataSet;
	
	/**
	 * Set up the context
	 * @throws Exception
	 */
	@Before
	public void setUp() throws Exception {
		licenceDataSet.setup();
		licenceId = Data.LICENCE_1.getId();
	}

	/**
	 * Clear the context
	 * @throws Exception
	 */
	@After
	public void tearDown() throws Exception {
		licenceDataSet.clear();
	}

	/**
	 * Test the createLicence(Licence) method : the DAO must have an additionnal entity after the creation
	 */
	@Test
	public void testCreateLicence() {
		int count = (int) licenceDAO.count();

		Licence l = new Licence();
		l.setLicenceKey("k");
		l.setClient(Data.CLIENT_1);
		l.setProduct(Data.PRODUCT_1);
		l.setMaintenance(Data.MAINTENANCE_FULL);
		
		licenceDAO.createLicence(l);
		assertEquals(count + 1, licenceDAO.count());
	}

	/**
	 * Test if the createLicence(Licence) method throws a {@link InvalidDataAccessApiUsageException}
	 * by passing a <code>null</code> {@link Licence}
	 */
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void testCreateNullLicence() {
		licenceDAO.createLicence(null);
	}

	/**
	 * Test if the DAO have no more {@link Licence} entities after the call of deleteAll() method
	 */
	@Test
	public void testDeleteAll() {
		licenceDAO.deleteAll();
		assertEquals(0, licenceDAO.count());
	}

	/**
	 * Test if the findAll() method return a correct and complete list of all licences. The following conditions are checked :<br/>
	 * <ul>
	 * <li>2 licences are in the list</li>
	 * <li>The list contains the licence "licence"</li>
	 * <li>The list contains the licence "tmp"</li>
	 * </ul>
	 */
	@Test
	public void testFindAll() {
		List<Licence> licences = licenceDAO.findAll();
		assertEquals(2, licences.size());
		assertTrue(licences.contains(Data.LICENCE_1));
		assertTrue(licences.contains(Data.LICENCE_2));
	}

	/**
	 * Test if the result of findByLicenceKey(String) is correct
	 */
	@Test
	public void testFindByLicenceKey() {
		assertEquals(Data.LICENCE_2, licenceDAO.findByLicenceKey("key"));
	}

	/**
	 * Test if the findByLicenceKey(String) method throws an {@link EmptyResultDataAccessException} if the given argument is null
	 */
	@Test(expected = EmptyResultDataAccessException.class)
	public void testFindByNullLicenceKey() {
		licenceDAO.findByLicenceKey(null);
	}

	/**
	 * Test if the modification of an entity has been correctly done
	 */
	@Test
	public void testUpdateLicence() {
		Data.LICENCE_2.setLicenceKey("tmp");
		licenceDAO.updateLicence(Data.LICENCE_2);
		assertEquals("tmp", licenceDAO.findByLicenceKey("tmp").getLicenceKey());
	}

	/**
	 * Test if the updateLicence(Licence) method throws an {@link InvalidDataAccessApiUsageException} if the given argument is null
	 */
	@Test(expected = InvalidDataAccessApiUsageException.class)
	public void testUpdateNullLicence() {
		licenceDAO.updateLicence(null);
	}

	/**
	 * Test the deletion of a {@link Licence}. We check that the deleted {@link Licence} can't be found.
	 */
	@Test
	public void testDeleteLicence() {
		licenceDAO.deleteLicence(Data.LICENCE_1);
		assertNull(licenceDAO.findById(licenceId));
	}

	/**
	 * Test if a {@link NullPointerException} is thrown by trying to delete a <code>null</code> {@link Licence}
	 */
	@Test(expected = NullPointerException.class)
	public void testDeleteNullLicence() {
		licenceDAO.deleteLicence(null);
	}

	/**
	 * Test if all licences with the specified maintenance type are found. The
	 * following conditions are checked : <br/>
	 * <ul>
	 * <li>The list contains 2 licences</li>
	 * <li>The list contains the licence "licence"</li>
	 * <li>The list contains the licence "tmp"</li>
	 * </ul>
	 */
	@Test
	public void testFindByMaintenance() {
		List<Licence> licences = licenceDAO.findByMaintenance(Data.MAINTENANCE_NONE);

		assertEquals(2, licences.size());
		assertTrue(licences.contains(Data.LICENCE_1));
		assertTrue(licences.contains(Data.LICENCE_2));
	}

	/**
	 * Test if an {@link Licence} can be found by its ID
	 */
	@Test
	public void testFindById() {
		assertEquals(Data.LICENCE_1, licenceDAO.findById(licenceId));
	}

	/**
	 * Test some {@link Licence} {@link Query}.
	 * First, we create a {@link Query} without filters.
	 * Then, after each execution of this query, we add a filter.
	 * The following scenario is executed :<br/>
	 * <ul>
	 * <li>0 filters : 2 licences found</li>
	 * <li>Filter () added : 1 user found</li>
	 * <li>Filter () added : no user found</li>
	 * </ul>
	 */
	@Test
	public void testFilter() {
		
		List<Licence> licences;
		Query<Licence> query = new Query<Licence>(Licence.class);
		
		licences = licenceDAO.filter(query);
		assertEquals(2, licences.size());
		
		// TODO test some filters
	}
	
	/**
	 * Test the count() method
	 */
	@Test
	public void testCount(){
		assertEquals(2, licenceDAO.count());
	}

}
