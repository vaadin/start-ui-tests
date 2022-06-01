package com.example.application.views.list;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.example.application.data.entity.Company;
import com.example.application.data.entity.Contact;
import com.example.application.data.entity.Status;
import com.example.application.data.service.CrmService;

public class MockCrmService extends CrmService {

    private static final AtomicInteger contactIdGenerator = new AtomicInteger();

    final static List<Status> statusList = Stream
            .of("Imported lead", "Not contacted", "Contacted", "Customer",
                    "Closed (lost)")
            .map(Status::new).collect(Collectors.toUnmodifiableList());
    final static List<Company> companyList = Stream
            .of("Avaya Inc.", "Phillips Van Heusen Corp.", "AutoZone, Inc.",
                    "Laboratory Corporation of America Holdings",
                    "Linens 'n Things Inc.")
            .map(Company::new).collect(Collectors.toUnmodifiableList());
    final LinkedHashSet<Contact> contactList = new LinkedHashSet<>(List.of(
            createContact("Eula", "Lane", "eula.lane@jigrormo.ye"),
            createContact("Barry", "Rodriquez", "barry.rodriquez@zun.mm"),
            createContact("Eugenia", "Selvi", "eugenia.selvi@capfad.vn"),
            createContact("Alejandro", "Miles", "alejandro.miles@dec.bn"),
            createContact("Cora", "Tesi", "cora.tesi@bivo.yt"),
            createContact("Marguerite", "Ishii", "marguerite.ishii@judbilo.gn"),
            createContact("Mildred", "Jacobs", "mildred.jacobs@joraf.wf"),
            createContact("Gene", "Goodman", "gene.goodman@kem.tl"),
            createContact("Lettie", "Bennett", "lettie.bennett@odeter.bb"),
            createContact("Mabel", "Leach", "mabel.leach@lisohuje.vi"),
            createContact("Jordan", "Miccinesi", "jordan.miccinesi@duod.gy"),
            createContact("Marie", "Parkes", "marie.parkes@nowufpus.ph"),
            createContact("Rose", "Gray", "rose.gray@kagu.hr"),
            createContact("Garrett", "Stokes", "garrett.stokes@fef.bg"),
            createContact("Barbara", "Matthieu", "barbara.matthieu@derwogi.jm"),
            createContact("Jean", "Rhodes", "jean.rhodes@wehovuce.gu"),
            createContact("Jack", "Romoli", "jack.romoli@zamum.bw"),
            createContact("Pearl", "Holden", "pearl.holden@dunebuh.cr"),
            createContact("Belle", "Montero", "belle.montero@repiwid.si"),
            createContact("Olive", "Molina", "olive.molina@razuppa.ga"),
            createContact("Minerva", "Todd", "minerva.todd@kulmenim.ad"),
            createContact("Bobby", "Pearson", "bobby.pearson@ib.kg"),
            createContact("Larry", "Ciappi", "larry.ciappi@ba.lk"),
            createContact("Ronnie", "Salucci", "ronnie.salucci@tohhij.lv"),
            createContact("Walter", "Grossi", "walter.grossi@tuvo.sa"),
            createContact("Frances", "Koopmans", "frances.koopmans@foga.tw"),
            createContact("Frances", "Fujimoto",
                    "frances.fujimoto@uswuzzub.jp"),
            createContact("Olivia", "Vidal", "olivia.vidal@hivwerip.vc")));

    public MockCrmService() {
        super(null, null, null);
    }

    @Override
    public List<Contact> findAllContacts(String stringFilter) {
        if (stringFilter == null || stringFilter.isEmpty()) {
            return List.copyOf(contactList);
        } else {
            String loweCaseFilter = stringFilter.toLowerCase(Locale.ROOT);
            return contactList.stream()
                    .filter(c -> c.getFirstName().toLowerCase(Locale.ROOT)
                            .contains(loweCaseFilter)
                            || c.getLastName().toLowerCase(Locale.ROOT)
                                    .contains(loweCaseFilter))
                    .collect(Collectors.toList());
        }
    }

    @Override
    public long countContacts() {
        return contactList.size();
    }

    @Override
    public void deleteContact(Contact contact) {
        contactList.remove(contact);
    }

    @Override
    public void saveContact(Contact contact) {
        if (contact.getId() == null) {
            contact.setId(contactIdGenerator.incrementAndGet());
        }
        contactList.add(contact);
    }

    @Override
    public List<Company> findAllCompanies() {
        return companyList;
    }

    @Override
    public List<Status> findAllStatuses() {
        return statusList;
    }

    private Contact createContact(String firstName, String lastName,
            String email) {
        Contact contact = new Contact();
        contact.setId(contactIdGenerator.incrementAndGet());
        contact.setFirstName(firstName);
        contact.setLastName(lastName);
        contact.setEmail(email);

        Random r = ThreadLocalRandom.current();
        contact.setCompany(companyList.get(r.nextInt(companyList.size())));
        contact.setStatus(statusList.get(r.nextInt(statusList.size())));
        return contact;
    }
}
