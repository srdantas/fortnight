package com.fortnight.gateways.database.mysql.adapters;

import com.fortnight.domains.Transfer;
import com.fortnight.gateways.database.mysql.entities.TransferEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TransferEntityAdapter {

    TransferEntity from(Transfer transfer);

    Transfer to(TransferEntity entity);
}
