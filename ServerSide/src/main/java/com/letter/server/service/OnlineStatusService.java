package com.letter.server.service;

import com.letter.server.dao.entity.OnlineStatusEntity;
import com.letter.server.dao.repository.OnlineStatusRepository;
import com.letter.server.dto.OnlineStatusDto;
import com.letter.server.mapper.OnlineStatusMapper;
import com.letter.server.service.exception.ServiceException;
import com.letter.server.service.validation.Validator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@AllArgsConstructor
public class OnlineStatusService {

    private final OnlineStatusRepository onlineStatusRepository;

    private final OnlineStatusMapper onlineStatusMapper;

    private final Validator<OnlineStatusDto> validator;

    public List<OnlineStatusDto> findAll() {
        List<OnlineStatusEntity> onlineStatusEntities = StreamSupport.stream(onlineStatusRepository.findAll().spliterator(), false).collect(Collectors.toList());

        return onlineStatusEntities.stream().map(onlineStatusMapper::onlineStatusEntityToDto).collect(Collectors.toList());
    }

    public OnlineStatusDto findById(OnlineStatusDto onlineStatusDto) throws ServiceException {
        validator.validateId(onlineStatusDto);

        OnlineStatusEntity onlineStatusEntity;

        try {
            onlineStatusEntity = onlineStatusRepository.findById(onlineStatusDto.getId()).orElseThrow(EntityNotFoundException::new);
        } catch (Exception ex) {
            throw new ServiceException("Exception while finding onlineStatusEntity with userId=" + onlineStatusDto.getUserId(), ex);
        }

        return onlineStatusMapper.onlineStatusEntityToDto(onlineStatusEntity);
    }

    public OnlineStatusDto save(OnlineStatusDto onlineStatusDto) throws ServiceException {
        validator.validate(onlineStatusDto);

        OnlineStatusDto response;

        try {
            OnlineStatusEntity onlineStatusEntity = onlineStatusMapper.onlineStatusDtoToEntity(onlineStatusDto);

            response = onlineStatusMapper.onlineStatusEntityToDto(onlineStatusRepository.save(onlineStatusEntity));
        } catch (Exception ex) {
            throw new ServiceException("Exception while mapping and saving onlineStatusEntity with userId=" + onlineStatusDto.getUserId(), ex);
        }

        return response;
    }


    public OnlineStatusDto edit(OnlineStatusDto onlineStatusDto) throws ServiceException {
        validator.validateId(onlineStatusDto);

        OnlineStatusDto responseDto;

        try {
            OnlineStatusEntity onlineStatusEntity =  onlineStatusRepository.findById(onlineStatusDto.getId()).orElseThrow(EntityNotFoundException::new);

            OnlineStatusEntity responseEntity = onlineStatusMapper.editOnlineStatusDtoToEntity(onlineStatusEntity, onlineStatusDto);
            responseEntity = onlineStatusRepository.save(responseEntity);

            responseDto = onlineStatusMapper.onlineStatusEntityToDto(responseEntity);
        } catch (Exception ex) {
            throw new ServiceException("Exception while editing OnlineStatusDto object, id=" + onlineStatusDto.getId(), ex);
        }

        return responseDto;
    }

    public void delete(OnlineStatusDto onlineStatusDto) throws ServiceException {
        validator.validateId(onlineStatusDto);

        try {
            OnlineStatusEntity entity = onlineStatusMapper.onlineStatusDtoToEntity(onlineStatusDto);

            onlineStatusRepository.delete(entity);
        } catch (Exception ex) {
            throw new ServiceException("Exception while deleting onlineStatusEntity with id=" + onlineStatusDto.getId(), ex);
        }
    }
}
