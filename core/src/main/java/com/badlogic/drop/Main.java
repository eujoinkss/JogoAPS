package com.badlogic.drop;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {
    private boolean isOnGround = true;
    private boolean olhandoDireita = true;
    private boolean isWalking = false;
    private float velocidadeY = 6f;
    private float tempoAnim = 0;
    // Instanciando objetos da biblioteca GDX
    private Texture background;
    private Texture player;
    private Texture chao;
    private Texture plataforma;
    private Texture grade;
    private Texture inimigo; 
    private Texture projetil;
    private Texture run1;
    private Texture run2;
    private Texture run3;
    private Texture run4;
    private Texture run5;
    private Texture run6;
    private Texture run7;
    private Texture run8;
    private Texture melee1;
    private Texture melee2;
    private Texture melee3;
    private Texture melee4;
    private Texture melee5;
    private Texture ranged1;
    private Texture ranged2;
    private Texture ranged3;
    private Sprite playerIdle;
    private FitViewport viewport;
    private SpriteBatch spriteBatch;
    private OrthographicCamera camera;
    private Rectangle chaoRet;
    private Array<Rectangle> plataformas;
    private Array<Inimigo> inimigos;
    private TextureRegion idleFrame;
    private Animation<TextureRegion> animCorrer;
    private Animation<TextureRegion> animMelee;
    private Animation<TextureRegion> animRanged;
    
    @Override
    public void create() {
        // Método para "Construir" o jogo
        // Atribuindo valores aos objetos do tipo "Textura", esses valores são imagem salvas no diretório do projeto
        background = new Texture("fundo.png");
        player = new Texture("Valdomir0.png");
        chao = new Texture("floor.png");
        plataforma = new Texture("platform.png");
        grade = new Texture("grade.png");
        inimigo = new Texture("fantasma1.png");
        projetil = new Texture("projectile.png");
        
        //Criando texturas que vão dar animação de movimento ao personagem
        run1 = new Texture("corrida1.png");
        run2 = new Texture("corrida2.png");
        run3 = new Texture("corrida3.png");
        run4 = new Texture("corrida4.png");
        run5 = new Texture("corrida5.png");
        run6 = new Texture("corrida6.png");
        run7 = new Texture("corrida7.png");
        run8 = new Texture("corrida8.png");
        
        TextureRegion frameCorrida1 = new TextureRegion(run1);
        TextureRegion frameCorrida2 = new TextureRegion(run2);
        TextureRegion frameCorrida3 = new TextureRegion(run3);
        TextureRegion frameCorrida4 = new TextureRegion(run4);
        TextureRegion frameCorrida5 = new TextureRegion(run5);
        TextureRegion frameCorrida6 = new TextureRegion(run6);
        TextureRegion frameCorrida7 = new TextureRegion(run7);
        TextureRegion frameCorrida8 = new TextureRegion(run8);
        idleFrame = new TextureRegion(player);
        
        melee1 = new Texture("melee1.png");
        melee2 = new Texture("melee2.png");
        melee3 = new Texture("melee3.png");
        melee4 = new Texture("melee4.png");
        melee5 = new Texture("melee5.png");
        
        TextureRegion frameMelee1 = new TextureRegion(melee1);
        TextureRegion frameMelee2 = new TextureRegion(melee2);
        TextureRegion frameMelee3 = new TextureRegion(melee3);
        TextureRegion frameMelee4 = new TextureRegion(melee4);
        TextureRegion frameMelee5 = new TextureRegion(melee5);
        
        ranged1 = new Texture("fantasma1.png");
        ranged2 = new Texture("fantasma2.png");
        ranged3 = new Texture("fantasma3.png");
        
        TextureRegion frameRanged1 = new TextureRegion(ranged1);
        TextureRegion frameRanged2 = new TextureRegion(ranged2);
        TextureRegion frameRanged3 = new TextureRegion(ranged3);
        
        
        animCorrer = new Animation<>(0.1f,
                frameCorrida1,
                frameCorrida2,
                frameCorrida3,
                frameCorrida4,
                frameCorrida5,
                frameCorrida6,
                frameCorrida7,
                frameCorrida8);
        
        animMelee = new Animation<>(0.3f,
                frameMelee1,
                frameMelee2,
                frameMelee3,
                frameMelee4,
                frameMelee5);
        
        animRanged = new Animation<>(0.3f,
                frameRanged1,
                frameRanged2,
                frameRanged3);
        
        spriteBatch = new SpriteBatch();
        // O objeto "OrthographicCamera" é usado para criar uma câmera dinâmica que segue o personagem horizontalmente
        camera = new OrthographicCamera();
        // viewport controlará a janela que é usada para visualizarmos e interagirmos com o jogo
        viewport = new FitViewport(100,70, camera);
        
        // Aqui é criado um sprite do playergonista usando a textura anteriormene atribuida, para que ele possa ser controlado
        playerIdle = new Sprite(player);
        playerIdle.setSize(8, 8);
        playerIdle.setPosition(0, 4);
        
        // Usamos o objeto "Rectangle" para darmos uma colisão para o chão, a textura é atribuída no objeto "Texture"
        chaoRet = new Rectangle(0, 0, background.getWidth() * 3, 4);
        
        // Cria uma lista de retângulos menores para usarmos de plataformas
        plataformas = new Array<>();
        
        criarPlataformas();
        
        // Cria uma lista de objetos "Inimigo"
        inimigos = new Array<>();
        
        criarInimigos();
    }

    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        if(width <= 0 || height <= 0) return;
        // Aqui garantimos que quando a janela tiver suas dimensões alteradas, ela mudará de tamanho sem perda de qualidade
        viewport.update(width, height, true);
    }

    @Override
    public void render() {
        tempoAnim += Gdx.graphics.getDeltaTime();
        // Aqui estão os métodos que serão repetidos em cada frame da aplicação
        input();
        logic();
        draw();
    }
    
    private void draw(){
        // método para "desenhar" o mundo, mostrando todos os elementos gráficos
        // Como o fundo se repete três vezes para dar uma sensação de um mapa grande, colocamos que a largura do mundo é o triplo da largura do fundo
        final float largura = background.getWidth() * 3;
        final float altura = viewport.getWorldHeight();
        float cameraX = playerIdle.getX() + playerIdle.getWidth() / 2;
        TextureRegion currentFrame;
        //Limpando a tela para começar a exibição
        ScreenUtils.clear(Color.BLACK);
        // Aqui setamos a càmera para acompanhar o sprite do jogador e para parar de seguir nos extremos do mapa
        cameraX = Math.max(viewport.getWorldWidth() / 2, cameraX);
        cameraX = Math.min(largura - viewport.getWorldWidth() / 2, cameraX);
        camera.position.x = cameraX;
        camera.update();
        
        if(isWalking){
            currentFrame = animCorrer.getKeyFrame(tempoAnim, true);
        }else{
            currentFrame = idleFrame;
        }
        if(!olhandoDireita && !currentFrame.isFlipX()){
            currentFrame.flip(true, false);
        }
        if(olhandoDireita && currentFrame.isFlipX()){
            currentFrame.flip(true, false);
        }
        spriteBatch.setProjectionMatrix(camera.combined);
        // Aqui será feita a exibição
        spriteBatch.begin();
        
        spriteBatch.draw(background, 0, 0, background.getWidth(), altura);
        spriteBatch.draw(background, background.getWidth(), 0, background.getWidth(), altura);
        spriteBatch.draw(background, background.getWidth() * 2, 0, background.getWidth(), altura);
        for(int i = 0; i < largura; i += 4){
            spriteBatch.draw(chao, i, 0, 8, 4);
        }
        for(int i = 0; i < largura; i +=4){
            spriteBatch.draw(grade, i, 4, 4, 2);
        }
        for(Rectangle pl:plataformas){
            spriteBatch.draw(plataforma, pl.x, pl.y, pl.width, pl.height);
        }
        spriteBatch.draw(currentFrame, playerIdle.getX(), playerIdle.getY(), playerIdle.getWidth(), playerIdle.getHeight());
        
        for(int idx = 0; idx < inimigos.size; idx++){
            
            Inimigo i = inimigos.get(idx);
            spriteBatch.draw(i.getFrame(), i.spriteInimigo.getX(), i.spriteInimigo.getY(), i.spriteInimigo.getWidth(), i.spriteInimigo.getHeight());
            
            if(i instanceof Atirador){
                
                Atirador a = (Atirador) i;
                
                for(int j = 0; j < a.getProjeteis().size; j++){
                    
                    Projetil p = a.getProjeteis().get(j);
                    
                    p.projetilSprite.draw(spriteBatch);
                }
            }
        }
        
        spriteBatch.end();
        
    }
    
    private void input(){
        final int gravidade = 100;
        final int forcaPulo = 65;
        float velocidade = 30f;
        isWalking = false;
        
        // DeltaTime = O tempo entre os frames
        float delta = Gdx.graphics.getDeltaTime();
        
        
        // Mover o personagem para a direita
        if(Gdx.input.isKeyPressed(Input.Keys.D) || Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            
            isWalking = true;
            playerIdle.translateX(velocidade * delta);
            
            if(!olhandoDireita){
                olhandoDireita = true;
            }
          // Mover o personagem para a esquerda
        } else if(Gdx.input.isKeyPressed(Input.Keys.A) || Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            
            isWalking = true;
            playerIdle.translateX(-velocidade * delta);
            
            if(olhandoDireita){
                olhandoDireita = false;
            }
            
        } 
        
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE) && isOnGround || Gdx.input.isKeyPressed(Input.Keys.UP) && isOnGround){
            
            velocidadeY = forcaPulo;
            isOnGround = false;
            
        }
        velocidadeY -= gravidade * delta;
        playerIdle.setY(playerIdle.getY() + velocidadeY * delta);
        
        System.out.println(background.getWidth() * 3);
    }
    
    private void logic(){
        final float largura = background.getWidth() * 3;
        Rectangle playerRet = playerIdle.getBoundingRectangle();
        float delta = Gdx.graphics.getDeltaTime();
        float cameraEsq = camera.position.x - viewport.getWorldWidth() / 2;
        float cameraDir = camera.position.x + viewport.getWorldWidth() / 2;
        isOnGround = false;
        
        // Criando colisão entre o personagem e o chão
        if(playerRet.overlaps(chaoRet) && velocidadeY <= 0){
            
            playerIdle.setY(chaoRet.y + chaoRet.height);
            velocidadeY = 0;
            isOnGround = true;
            
        }
        
        // Criando uma colisão entre o personagem e a borda esquerda do mapa
        if(playerIdle.getX() < 0){
                playerIdle.setX(0);
            }
        
        // Criando uma colisão entre o personagem e a borda direita do mapa
        if(playerIdle.getX() > largura - playerIdle.getWidth()){
                playerIdle.setX(largura - playerIdle.getWidth());
            }
        
        //Criando colisão com cada uma das plataformas com o laço "for"
        for(Rectangle pl:plataformas){
            
            if(playerRet.overlaps(pl) && velocidadeY <= 0){
                
                playerIdle.setY(pl.y + pl.height);
                velocidadeY = 0;
                isOnGround = true;
                
                break;
            }
        }
        // Atualizando a movimentação dos inimigos APENAS quando estiver na tela do jogador, também chamando o metodo de resetar caso ele seja atingido
        for(int idx = 0; idx < inimigos.size; idx++){
            
            Inimigo i = inimigos.get(idx);
            
            float inimigoX = i.spriteInimigo.getX();
            float larguraInimigo = i.spriteInimigo.getWidth();
            
            boolean visivel = inimigoX + larguraInimigo >= cameraEsq && inimigoX <= cameraDir;
            
            if(visivel){
                
                i.atualizar(playerIdle, delta);
                        
            }
            if(i instanceof Atirador){
                Atirador a = (Atirador) i;
                a.atualizarProjetil(delta, largura);
            }
            
            if(i.spriteInimigo.getBoundingRectangle().overlaps(playerIdle.getBoundingRectangle())){
                
                reiniciarJogo();
                break;
            }
            
            if(i instanceof Atirador){
                
                Atirador a = (Atirador) i;
                
                for(int j = 0; j < a.getProjeteis().size; j++){
                    
                    if(a.getProjeteis().get(j).projetilSprite.getBoundingRectangle().overlaps(playerIdle.getBoundingRectangle())){
                        
                        reiniciarJogo();
                        break;
                    }
                }
            }
            
        }
        
    }
    
    private void criarPlataformas(){
        
        plataformas.add(new Rectangle(50, 20, 30, 3));
        plataformas.add(new Rectangle(65, 35, 30, 3));
        plataformas.add(new Rectangle(80, 20, 30, 3));
        plataformas.add(new Rectangle(160, 20, 30, 3));
        plataformas.add(new Rectangle(190, 35, 30, 3));
        plataformas.add(new Rectangle(250, 35, 30, 3));
        plataformas.add(new Rectangle(280, 50, 30, 3));
        plataformas.add(new Rectangle(280, 20, 30, 3));
        plataformas.add(new Rectangle(400, 20, 30, 3));
        plataformas.add(new Rectangle(400, 50, 30, 3));
        plataformas.add(new Rectangle(430, 35, 30, 3));
        plataformas.add(new Rectangle(460, 50, 30, 3));
        plataformas.add(new Rectangle(460, 20, 30, 3));
        
        
    }
    
    private void criarInimigos(){
        inimigos.add(new Lutador(melee1, animMelee, 100, 4));
        inimigos.add(new Lutador(melee1, animMelee, 120, 4));
        inimigos.add(new Lutador(melee1, animMelee, 280, 4));
        inimigos.add(new Lutador(melee1, animMelee, 310, 4));
        inimigos.add(new Lutador(melee1, animMelee, 410, 4));
        inimigos.add(new Atirador(ranged1, animRanged, projetil, 80, 39));
        inimigos.add(new Atirador(ranged1, animRanged, projetil, 265, 39));
        inimigos.add(new Atirador(ranged1, animRanged, projetil, 295, 54));
        inimigos.add(new Atirador(ranged1, animRanged, projetil, 415, 54));
        inimigos.add(new Atirador(ranged1, animRanged, projetil, 475, 54));
    }
    
    // Método invocado caso algum inimigo acerte o jogador
    private void reiniciarJogo(){
        playerIdle.setPosition(0, 8);
        velocidadeY = 0;
        isOnGround = false;
        
        for(Inimigo i : inimigos){
            i.resetar();
            
            if(i instanceof Atirador){
                Atirador a = (Atirador) i;
                
                a.limparProjeteis();
            }
        }
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void dispose() {
        // Destroy application's resources here.
    }
}