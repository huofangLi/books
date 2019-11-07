import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'reading',
        loadChildren: './reading/reading.module#BooksReadingModule'
      },
      {
        path: 'topic',
        loadChildren: './topic/topic.module#BooksTopicModule'
      },
      {
        path: 'book-classification',
        loadChildren: './book-classification/book-classification.module#BooksBookClassificationModule'
      },
      {
        path: 'book',
        loadChildren: './book/book.module#BooksBookModule'
      },
      {
        path: 'book-summary',
        loadChildren: './book-summary/book-summary.module#BooksBookSummaryModule'
      },
      {
        path: 'course',
        loadChildren: './course/course.module#BooksCourseModule'
      },
      {
        path: 'square',
        loadChildren: './square/square.module#BooksSquareModule'
      },
      {
        path: 'specicl-topic',
        loadChildren: './specicl-topic/specicl-topic.module#BooksSpeciclTopicModule'
      },
      {
        path: 'wish-list',
        loadChildren: './wish-list/wish-list.module#BooksWishListModule'
      },
      {
        path: 'experience',
        loadChildren: './experience/experience.module#BooksExperienceModule'
      },
      {
        path: 'note',
        loadChildren: './note/note.module#BooksNoteModule'
      },
      {
        path: 'file',
        loadChildren: './file/file.module#BooksFileModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BooksEntityModule {}
