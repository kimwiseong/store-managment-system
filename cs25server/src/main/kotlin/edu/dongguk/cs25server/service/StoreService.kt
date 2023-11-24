package edu.dongguk.cs25server.service

import edu.dongguk.cs25server.domain.Manager
import edu.dongguk.cs25server.domain.Store
import edu.dongguk.cs25server.dto.request.StoreRequestDto
import edu.dongguk.cs25server.dto.response.StoreReponseDto
import edu.dongguk.cs25server.exception.ErrorCode
import edu.dongguk.cs25server.exception.GlobalException
import edu.dongguk.cs25server.repository.ManagerRepository
import edu.dongguk.cs25server.repository.StoreRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class StoreService(private val storeRepository: StoreRepository, private val managerRepository: ManagerRepository) {
    //C
    fun createStore(requestDto: StoreRequestDto): Boolean? {
        storeRepository.findTop1ByNameOrCallNumber(requestDto.name, requestDto.callNumber)
                ?.let { throw GlobalException(ErrorCode.DUPLICATION_STORE) }

        val manager: Manager = managerRepository.findByIdOrNull(requestDto.managerId)
                ?: throw GlobalException(ErrorCode.NOT_FOUND_MANAGER)

        storeRepository.save(Store(name = requestDto.name,
                address = requestDto.address,
                callNumber = requestDto.callNumber,
                thumbnail = requestDto.thumbnail,
                manager = manager))
        return true
    }
    //R
    //보류
    fun searchByName(name: String) : List<StoreReponseDto> = storeRepository.findByNameContains(name)
        .map(Store::toResponse)
        .ifEmpty { throw GlobalException(ErrorCode.NOT_FOUND_STORE) }



    //U
    //보류

    //D
    //디비에서 지우죠!

}