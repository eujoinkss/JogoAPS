/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.badlogic.drop;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 *
 * @author julro
 */
public class Inimigo {
    public Sprite spriteInimigo;
    public float velocidade = 18f;
    public boolean olhandoDireita = true;
    public float spawnX;
    public float spawnY;
    
    public Inimigo(Texture textura, float x, float y){
        
        spriteInimigo = new Sprite(textura);
        spriteInimigo.setSize(6, 10);
        spriteInimigo.setPosition(x, y);
        this.spawnX = x;
        this.spawnY = y;
        
    }
    
    public void atualizar(Sprite jogador, float delta){
        
        float playerX = jogador.getX();
        float enemyX = spriteInimigo.getX();
        float distancia = Math.abs(playerX - enemyX);
        
        if(distancia > 2){
            if(playerX > enemyX){
                spriteInimigo.translateX(velocidade * delta);
                olhandoDireita = true;
            }
            if(playerX < enemyX){
                spriteInimigo.translateX( -velocidade * delta);
                olhandoDireita = false;
            }
        }
                
    }
    
    public void resetar(){
        spriteInimigo.setPosition(spawnX, spawnY);
    }
    
}
