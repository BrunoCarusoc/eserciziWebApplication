package com.dipartimento.demowebapplications.persistence.dao;

import com.dipartimento.demowebapplications.model.Piatto;

import java.util.List;

public interface PiattoDao {

    public List<Piatto> findAll();

    public Piatto findByPrimaryKey(String nome);

    public void save(Piatto piatto);

    public void delete(Piatto piatto);

    public List<Piatto> findAllByRistoranteName(String ristoranteName);

}