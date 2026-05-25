/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.badlogic.drop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 *
 * @author julro
 */
public class Atirador extends Inimigo {
    private float tempoTiro = 0;
    private Texture texturaProjetil;
    private Array<Projetil> projeteis;

    public Atirador(Texture textura, Animation anim, Texture texturaProjetil, float x, float y){
        super(textura, anim, x, y);
        this.texturaProjetil = texturaProjetil;
        velocidade = 0;
        spriteInimigo.setSize(6, 10);
        projeteis = new Array<>();
    }

    @Override
    public void atualizar(Sprite jogador, float delta){
        atualizarAnimacao(delta);
        tempoTiro += delta;
        
        if(tempoTiro >= 1f){
            float distanciaX = jogador.getX() - spriteInimigo.getX();
            float distanciaY = jogador.getY() - spriteInimigo.getY();
            
            float distancia = (float) Math.sqrt(distanciaX * distanciaX + distanciaY * distanciaY);
            
            distanciaX /= distancia;
            distanciaY /= distancia;
            
            float velocidadeProjetil = 50f;
            
            projeteis.add(new Projetil(texturaProjetil, 
                    spriteInimigo.getX(), 
                    spriteInimigo.getY() + 3, 
                    distanciaX * velocidadeProjetil, 
                    distanciaY * velocidadeProjetil));
            
            tempoTiro = 0;
        }
        
    }
    
    public Array<Projetil> getProjeteis(){
        return projeteis;
    }
    
    public void atualizarProjetil(float delta, float larguraMapa){
        
        for(int i = projeteis.size - 1; i >= 0; i--){
            
            Projetil p = projeteis.get(i);
            p.atualizar(delta);
            
            if(p.projetilSprite.getX() > larguraMapa || p.projetilSprite.getX() < 0){
                projeteis.removeIndex(i);
            }
        }
    }
    
    public void limparProjeteis(){
        projeteis.clear();
    }
    
}
