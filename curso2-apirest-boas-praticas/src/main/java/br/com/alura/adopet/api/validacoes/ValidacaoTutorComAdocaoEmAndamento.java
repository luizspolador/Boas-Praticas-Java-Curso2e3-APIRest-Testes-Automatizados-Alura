package br.com.alura.adopet.api.validacoes;

import br.com.alura.adopet.api.dto.SolicitacaoAdocaoDto;
import br.com.alura.adopet.api.exception.ValidacaoException;
import br.com.alura.adopet.api.model.Adocao;
import br.com.alura.adopet.api.model.StatusAdocao;
import br.com.alura.adopet.api.model.Tutor;
import br.com.alura.adopet.api.repository.AdocaoRepository;
import br.com.alura.adopet.api.repository.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ValidacaoTutorComAdocaoEmAndamento implements ValidacaoSolicitacaoAdocaoPet{

    @Autowired
    private AdocaoRepository adocaoRepository;

    @Autowired
    private TutorRepository tutorRepository;

    @Override
    public void validarPet(SolicitacaoAdocaoDto dto){
        List<Adocao> adocoes = adocaoRepository.findAll();
        Tutor tutor = tutorRepository.getReferenceById(dto.idTutor());
        for (Adocao a : adocoes) {
            if (a.getTutor() == tutor && a.getStatus() == StatusAdocao.AGUARDANDO_AVALIACAO) {
                throw new ValidacaoException("Tutor já possui outra adoção aguardando avaliação!");
            }
        }
    }
}