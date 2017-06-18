package com.lazur.services;


import com.lazur.entities.Family;
import com.lazur.models.view.FamilyBidnignModel;
import com.lazur.models.view.FamilyViewModel;

import java.util.List;

public interface FamilyService {

    Family findFamily(String name, String productName, String productCategory);

    void save(FamilyBidnignModel familyBidnignModel);

    List<FamilyViewModel> findAllByCategoryAndModel(String category, String model);

    FamilyViewModel findByFamily(Long id);

    void delete(Long familyId);

    void update(Long familyId, FamilyViewModel familyBidnignModel);
}

