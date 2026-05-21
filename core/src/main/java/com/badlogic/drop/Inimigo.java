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
public abstract class Inimigo {
    public Sprite spriteInimigo;
    protected float velocidade;
    public float spawnX;
    public float spawnY;
    
    public Inimigo(Texture textura, float x, float y){
        
        spriteInimigo = new Sprite(textura);
        spriteInimigo.setSize(6, 10);
        spriteInimigo.setPosition(x, y);
        this.spawnX = x;
        this.spawnY = y;
        
    }
    
    public abstract void atualizar(Sprite jogador, float delta);
    
    public void resetar(){
        spriteInimigo.setPosition(spawnX, spawnY);
    }
    
}
