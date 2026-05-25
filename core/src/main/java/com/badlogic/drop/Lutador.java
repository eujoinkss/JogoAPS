/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.badlogic.drop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 *
 * @author julro
 */
public class Lutador extends Inimigo {
    
    public Lutador(Texture textura, Animation<TextureRegion> anim, float x, float y){
        super(textura, anim, x, y);
        velocidade = 18f;
        spriteInimigo.setSize(8, 8);
    };
    
    @Override
    public void atualizar(Sprite jogador, float delta){
        atualizarAnimacao(delta);
        float playerX = jogador.getX();
        float enemyX = spriteInimigo.getX();
        float distancia = Math.abs(playerX - enemyX);
        
        if(distancia > 2){
            if(playerX > enemyX){
                spriteInimigo.translateX(velocidade * delta);
            }
            if(playerX < enemyX){
                spriteInimigo.translateX(-velocidade * delta);
            }
        }
    }
    
}
