package com.lambdaschool.bookstore.services;

import com.lambdaschool.bookstore.exceptions.ResourceFoundException;
import com.lambdaschool.bookstore.exceptions.ResourceNotFoundException;
import com.lambdaschool.bookstore.models.Section;
import com.lambdaschool.bookstore.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service(value = "sectionService")
public class SectionServiceImpl implements SectionService
{
    @Autowired
    private SectionRepository sectionrepos;

    @Override
    public List<Section> findAll(Pageable pageable)
    {
        List<Section> list = new ArrayList<>();
        sectionrepos.findAll(pageable).iterator().forEachRemaining(list::add);
        return list;
    }

    @Override
    public Section findSectionById(long id)
    {
        return sectionrepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Long.toString(id)));
    }

    @Override
    public void delete(long id)
    {
        if (sectionrepos.findById(id).isPresent())
        {
            sectionrepos.deleteById(id);
        } else
        {
            throw new ResourceNotFoundException(Long.toString(id));
        }
    }

    @Transactional
    @Override
    public Section save(Section section)
    {
        Section newSection = new Section();

        newSection.setSectionid(section.getSectionid());
        newSection.setSectionname(section.getSectionname());

        return sectionrepos.save(newSection);
    }

    @Override
    public Section update(Section section, long id)
    {
        Section currentSection = sectionrepos.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(Long.toString(id)));

        if(section.getSectionid() != currentSection.getSectionid())
        {
            currentSection.setSectionid(section.getSectionid());
        }
        if(section.getSectionname() != null)
        {
            currentSection.setSectionname(section.getSectionname());
        }

        return sectionrepos.save(currentSection);
    }
}
