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
public class Projetil {
    public Sprite projetilSprite;
    private float velocidadeX;
    private float velocidadeY;
    
    public Projetil(Texture textura, float x, float y, float velocidadeX, float velocidadeY){
        
        projetilSprite = new Sprite(textura);
        projetilSprite.setSize(3, 2);
        projetilSprite.setPosition(x, y);
        
        this.velocidadeX = velocidadeX;
        this.velocidadeY = velocidadeY;
    }
    
    public void atualizar(float delta){
        projetilSprite.translate(velocidadeX * delta,velocidadeY * delta);
    }
}
